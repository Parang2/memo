package com.example.menojang;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    public ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;


    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return listViewItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        final Context context = viewGroup.getContext();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, viewGroup, false);
        }
        TextView titleTextView = (TextView) convertView.findViewById(R.id.textView1) ;
        TextView timeTextView = (TextView) convertView.findViewById(R.id.textView2) ;

        ListViewItem listViewItem = listViewItemList.get(i);

        titleTextView.setText(listViewItem.getTitle());
        timeTextView.setText(listViewItem.getTime());

        return convertView;
    }

    public void addItem(String title, String time, String memo){
        ListViewItem item = new ListViewItem();

        item.setName(title);
        item.setTime(time);
        item.setMemo(memo);

        listViewItemList.add(item);
    }
}
