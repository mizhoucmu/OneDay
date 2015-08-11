package edu.cmu.ebiz.oneday;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.cmu.ebiz.oneday.bean.TodoItemBean;
import edu.cmu.ebiz.oneday.utils.OnedayAdapter;
import edu.cmu.ebiz.oneday.utils.SlideListView2;
import edu.cmu.ebiz.oneday.view.NewItem;


public class Main extends Activity {

    public static final int NEW_ITEM_REQUEST = 200;
    private List<TodoItemBean> todoList = new ArrayList<TodoItemBean>();
    private SlideListView2 listView;
    private OnedayAdapter mAdapter;
    private SensorManager sensorManager;
    private TextView buffertimetv;
    private TodoItemBean bufferItem;
    private Handler timerHanlder;
    CountDownRunnable countDownRunnable;
    private int currentCountdownIndex = -1;
    Vibrator vibrator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (SlideListView2) findViewById(R.id.onedaylistview);
        mAdapter = new OnedayAdapter(this, todoList);
        listView.setAdapter(mAdapter);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);


        bufferItem = new TodoItemBean("Buffer", 60);
        buffertimetv = (TextView) findViewById(R.id.buffertime);
        buffertimetv.setText(bufferItem.getExpectedTime());
        buffertimetv.setBackgroundColor(Color.parseColor("#6F89AB"));
        buffertimetv.setTextColor(Color.parseColor("#FFFFFF"));
        buffertimetv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewItem();
            }
        });
        updateBufferTimeleft();


        countDownRunnable = new CountDownRunnable();
        timerHanlder = new Handler();
        timerHanlder.postDelayed(countDownRunnable, 1000);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentCountdownIndex != position) { //有其他的timer在运行
                    if (currentCountdownIndex == -1) {
                        bufferItem.setStatus(TodoItemBean.PAUSED);
                        buffertimetv.setBackgroundColor(Color.parseColor("#EEEEEE"));
                        buffertimetv.setTextColor(Color.parseColor("#000000"));

                    } else {
                        todoList.get(currentCountdownIndex).setStatus(TodoItemBean.PAUSED);
                    }
                    currentCountdownIndex = position;
                    todoList.get(position).setStatus(TodoItemBean.ING);
                } else {
                    todoList.get(position).setStatus(TodoItemBean.PAUSED);
                    currentCountdownIndex = -1;
                    buffertimetv.setBackgroundColor(Color.parseColor("#6F89AB"));
                    buffertimetv.setTextColor(Color.parseColor("#FFFFFF"));

                }
            }
        });
    }


    private void updateBufferTimeleft() {
        Calendar c = Calendar.getInstance();
        long now = c.getTimeInMillis();

        c.add(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        long timeleft = c.getTimeInMillis() - now;
        for (int i = 0; i < todoList.size(); i++) {
            timeleft -= todoList.get(i).getTimeleft() * 1000;
        }
        bufferItem.setTimeLeft((int) (timeleft / 1000));
        buffertimetv.setText(bufferItem.getTimeleftString());

    }


    class CountDownRunnable implements Runnable {
        @Override
        public void run() {
            if (currentCountdownIndex == -1) {
                bufferItem.countDown();
                buffertimetv.setText(bufferItem.getTimeleftString());

            } else {
                if (!todoList.get(currentCountdownIndex).countDown()) {
                    // used up
                    long[] pattern = {0, 100, 100};
                    vibrator.vibrate(pattern,1);
                }
            }
            mAdapter.notifyDataSetChanged();
            timerHanlder.postDelayed(countDownRunnable, 1000);
        }
    }

    private void addNewItem() {
        Intent it = new Intent(Main.this, NewItem.class);
        startActivityForResult(it, NEW_ITEM_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_ITEM_REQUEST && resultCode == RESULT_OK) {
            String item_title = data.getStringExtra("NEW_ITEM_TITLE");
            int item_duration_min = data.getIntExtra("NEW_ITEM_DUR_MIN", 0);
            TodoItemBean todoItemBean = new TodoItemBean(item_title, item_duration_min);
            this.todoList.add(todoItemBean);
            updateBufferTimeleft();
            mAdapter.notifyDataSetChanged();
        }
    }
}
