package edu.cmu.ebiz.oneday.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.cmu.ebiz.oneday.Main;
import edu.cmu.ebiz.oneday.R;
import edu.cmu.ebiz.oneday.bean.TodoItemBean;

public class NewItem extends Activity {

    EditText title;
    EditText hour;
    EditText min;
    Button okBtn;
    Button cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);
        title = (EditText) findViewById(R.id.new_item_title);
        title.requestFocus();
        hour = (EditText) findViewById(R.id.duration_hour);
        min = (EditText) findViewById(R.id.duration_min);
        min.setSelectAllOnFocus(true);
        okBtn = (Button) findViewById(R.id.ok_button);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(NewItem.this,Main.class);
                String hourstr = hour.getText().toString();
                hourstr = hourstr.trim();
                int hourInt = 0;
                if (hourstr != null && hourstr.length() > 0) {
                    hourInt = Integer.parseInt(hourstr);
                }

                String minstr = min.getText().toString();
                minstr = minstr.trim();
                int minInt = 0;
                if (minstr != null && minstr.length() > 0) {
                    minInt = Integer.parseInt(minstr);
                }
                int totalMin = hourInt * 60 + minInt;

                it.putExtra("NEW_ITEM_TITLE",title.getText().toString());
                it.putExtra("NEW_ITEM_DUR_MIN",totalMin);
                setResult(RESULT_OK,it);
                finish();

            }
        });
        cancelBtn = (Button) findViewById(R.id.cancel_button);
        cancelBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent it = new Intent();
                setResult(RESULT_CANCELED,it);
                finish();
            }
        });

    }

}
