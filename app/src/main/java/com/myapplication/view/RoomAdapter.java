package com.myapplication.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.myapplication.R;
import com.myapplication.databinding.RoomItemLayoutBinding;
import com.myapplication.model.RoomModel;
import com.myapplication.onClickInterface.OnClikItemInterfaceRoom;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {

    List<RoomModel> roomsModelList;
    private final OnClikItemInterfaceRoom _onClikItemInterfaceRoom;

    public RoomAdapter(OnClikItemInterfaceRoom onClikItemInterfaceRoom){
        _onClikItemInterfaceRoom = onClikItemInterfaceRoom;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RoomItemLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.room_item_layout,
                parent,
                false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(roomsModelList!=null){
            RoomModel roomModel = roomsModelList.get(position);

            holder.binding.setRoomModel(roomModel);
            holder.binding.setListener(_onClikItemInterfaceRoom);
        }
    }

    @Override
    public int getItemCount() {
        if(roomsModelList!=null){
            return roomsModelList.size();
        }else return 0;
    }

    public void setRooms(List<RoomModel> rooms){
        roomsModelList = rooms;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RoomItemLayoutBinding binding;

        public ViewHolder(@NonNull RoomItemLayoutBinding binding){
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
