package com.myapplication.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.myapplication.R;
import com.myapplication.databinding.RoomItemLayoutBinding;
import com.myapplication.model.Room;
import com.myapplication.onClickInterface.OnClikItemInterfaceRoom;
import com.myapplication.viewmodel.RoomViewModel;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {

    List<Room> roomsModelList;
    private final Context context;
    private RoomViewModel roomViewModel;

    public RoomAdapter(Context context,List<Room> roomList, RoomViewModel roomViewModel){
        this.context = context;
        this.roomsModelList = roomList;
        this.roomViewModel = roomViewModel;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_item_layout,parent,false);
        return new RoomAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(roomsModelList!=null){
            Room room = roomsModelList.get(position);

        }
    }

    @Override
    public int getItemCount() {
        if(roomsModelList!=null){
            return roomsModelList.size();
        }else return 0;
    }

    public void setRooms(List<Room> rooms){
        roomsModelList = rooms;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName,tvRoomNumber,tvDescription, tvCapacity, tvMaterial, tvPrice, tvAvailable, tvState;
        ImageView ivDelete, ivEdit, ivDisabled;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvName = itemView.findViewById(R.id.txtRName);
            tvCapacity = itemView.findViewById(R.id.txtCapacity);
            tvPrice = itemView.findViewById(R.id.txtPricePerMonth);
            tvAvailable = itemView.findViewById(R.id.txtAvailable);
            tvState = itemView.findViewById(R.id.txtState);

        }
    }
}
