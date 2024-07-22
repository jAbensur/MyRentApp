package com.myapplication.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapplication.R;
import com.myapplication.model.Room;
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
        Room room = roomsModelList.get(position);
        holder.tvName.setText(room.nameR);
        holder.tvCapacity.setText(String.valueOf(room.capacity));
        holder.tvPrice.setText(String.valueOf(room.pricePerMonth));
        String available = room.available == 1 ? "Disponible" : "Ocupado";
        holder.tvAvailable.setText(available);
        String state = room.state == 1 ? "Activo" : "Inactivo";
        holder.tvState.setText(state);

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirmación");
                builder.setMessage("¿Estás seguro que deseas eliminar este elemento?");

                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        roomViewModel.deleteRoom(room);
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RoomAddActivity.class);
                intent.putExtra("model",room);
                context.startActivity(intent);
            }
        });

        holder.ivDisabled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message;

                if(room.state == 1){
                    message = "dar de baja";
                }else {
                    message = "habilitar";
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirmación");
                builder.setMessage("¿Estás seguro que desea "+message+" este elemento?");

                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(room.state == 1){
                            room.state = 0;
                        }else {
                            room.state = 1;
                        }
                        roomViewModel.updateRoom(room);
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        holder.ivInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RoomInfoActivity.class);
                intent.putExtra("model",room);
                context.startActivity(intent);
            }
        });

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
        ImageView ivDelete, ivEdit, ivDisabled, ivInfo;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvName = itemView.findViewById(R.id.txtRName);
            tvCapacity = itemView.findViewById(R.id.txtCapacity);
            tvPrice = itemView.findViewById(R.id.txtPricePerMonth);
            tvAvailable = itemView.findViewById(R.id.txtAvailable);
            tvState = itemView.findViewById(R.id.txtState);

            ivDelete = itemView.findViewById(R.id.imgDelete);
            ivEdit = itemView.findViewById(R.id.imgEdit);
            ivDisabled = itemView.findViewById(R.id.imgDisabled);
            ivInfo = itemView.findViewById(R.id.imgInfo);
        }
    }
}
