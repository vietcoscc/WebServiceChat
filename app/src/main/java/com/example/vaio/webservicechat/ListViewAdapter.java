package com.example.vaio.webservicechat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vaio on 11/30/2016.
 */

public class ListViewAdapter extends ArrayAdapter<ItemMessage> {
    private LayoutInflater inflater;
    private ArrayList<ItemMessage> arrItemMessage;

    public ListViewAdapter(Context context, ArrayList<ItemMessage> arrItemMessage) {
        super(context, android.R.layout.simple_list_item_1, arrItemMessage);
        inflater = LayoutInflater.from(context);
        this.arrItemMessage = arrItemMessage;
    }

    @Override
    public int getCount() {
        return arrItemMessage.size();
    }

    @NonNull
    @Override
    public View getView(int position, View v, ViewGroup parent) {
        ViewHolder viewHolder;
        if (v == null) {
            v = inflater.inflate(R.layout.item_list_view, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) v.findViewById(R.id.name);
            viewHolder.date = (TextView) v.findViewById(R.id.date);
            viewHolder.content = (TextView) v.findViewById(R.id.content);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }
        viewHolder.name.setText(arrItemMessage.get(position).getName());
        viewHolder.date.setText(arrItemMessage.get(position).getDate());
        viewHolder.content.setText(arrItemMessage.get(position).getContent());
        return v;
    }

    class ViewHolder {
        TextView name;
        TextView date;
        TextView content;
    }
}
