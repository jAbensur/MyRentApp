package com.myapplication.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.myapplication.model.Room;
import com.myapplication.model.Tenant;
import java.util.List;

public class RoomSpinnerAdapter extends ArrayAdapter<Room> {
    public RoomSpinnerAdapter(Context context, List<Room> rooms){
        super(context, android.R.layout.simple_spinner_item, rooms);
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_item, parent, false);
        }

        Room room = getItem(position);
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(room.getNameR());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }

        Room room = getItem(position);
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(room.getNameR());

        return convertView;
    }
}
