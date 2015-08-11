package edu.cmu.ebiz.oneday.utils;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import edu.cmu.ebiz.oneday.R;
import edu.cmu.ebiz.oneday.bean.TodoItemBean;

/**
 * Created by julie on 8/9/15.
 */
public class OnedayAdapter extends BaseAdapter{
    private List<TodoItemBean> mList;
    private LayoutInflater mInflater;

    public OnedayAdapter(Context context, List<TodoItemBean> list) {
        mList = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item,null);
            viewHolder.layout = (LinearLayout)convertView.findViewById(R.id.item);
            viewHolder.title = (TextView)convertView.findViewById(R.id.item_title);
            viewHolder.timer = (TextView)convertView.findViewById(R.id.timer);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TodoItemBean bean = mList.get(position);
        viewHolder.title.setText(bean.getTitle());
        viewHolder.timer.setText(bean.getTimeleftString());
        if (bean.getStatus() == TodoItemBean.ING) { //highlighted
            viewHolder.layout.setBackgroundColor(Color.parseColor("#6F89AB"));
            viewHolder.timer.setBackgroundColor(Color.parseColor("#6F89AB"));
            viewHolder.timer.setTextColor(Color.parseColor("#FFFF6F"));
            viewHolder.title.setBackgroundColor(Color.parseColor("#6F89AB"));
            viewHolder.title.setTextColor(Color.parseColor("#FFFFFF"));
        }
        else {// not highlighted
            viewHolder.layout.setBackgroundColor(Color.parseColor("#EEEEEE"));
            viewHolder.timer.setBackgroundColor(Color.parseColor("#EEEEEE"));
            viewHolder.timer.setTextColor(Color.parseColor("#ADADAD"));//grey
            viewHolder.title.setBackgroundColor(Color.parseColor("#EEEEEE"));
            viewHolder.title.setTextColor(Color.parseColor("#111111"));
        }
        return convertView;
    }

    class ViewHolder {
        public LinearLayout layout;
        public TextView title;
        public TextView timer;
    }
}
