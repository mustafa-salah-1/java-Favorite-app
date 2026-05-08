package com.example.favorite.Components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.favorite.Models.PlaceItem;
import com.example.favorite.R;

import java.util.List;

public class PlaceAdapter  extends BaseAdapter {
    private List<PlaceItem> list;
    private Context context;

    public PlaceAdapter(Context context, List<PlaceItem> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_place, parent, false);
        }

        PlaceItem item = list.get(position);
        TextView name = convertView.findViewById(R.id.text1);
        TextView sub = convertView.findViewById(R.id.text2);

        name.setText(item.getName());
        if (item.getDescription() != null && !item.getDescription().isEmpty()) {
            sub.setText(item.getDescription());
        } else {
            sub.setText(String.format("Lat: %.4f, Lon: %.4f", item.getLatitude(), item.getLongitude()));
        }

        return convertView;
    }
}