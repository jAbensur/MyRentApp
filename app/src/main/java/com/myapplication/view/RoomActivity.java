package com.myapplication.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.myapplication.R;
import com.myapplication.adapter.RoomAdapter;
import com.myapplication.databinding.ActivityRoomBinding;
import com.myapplication.model.RoomModel;
import com.myapplication.onClickInterface.OnClikItemInterfaceRoom;
import com.myapplication.viewmodel.RoomViewModel;

public class RoomActivity extends AppCompatActivity implements OnClikItemInterfaceRoom {

    private ActivityRoomBinding _binding;
    private RoomViewModel _roomViewModel;
    private RoomAdapter _adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _binding= ActivityRoomBinding.inflate(getLayoutInflater());
        setContentView(_binding.getRoot());
        _binding.RecyclerViewRoom.setLayoutManager(new LinearLayoutManager(this));
        _adapter = new RoomAdapter(this);
        _binding.RecyclerViewRoom.setAdapter(_adapter);

        getSupportActionBar().setTitle("Habitaciones");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        _binding.addRoom.setOnClickListener(view -> {
            startActivity(new Intent(RoomActivity.this, AddRoomActivity.class));
        });

        _roomViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(RoomViewModel.class);

    }

    @Override
    public boolean onSupportNavigateUp(){
        getOnBackPressedDispatcher().onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onClickItem(RoomModel roomModel, boolean isEdit) {
        if(isEdit){
            Intent intent = new Intent(RoomActivity.this,AddRoomActivity.class);
            intent.putExtra("model",roomModel);
            startActivity(intent);
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirmación");
            builder.setMessage("¿Estás seguro que deseas eliminar este elemento?");

            builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    _roomViewModel.deleteRoom(roomModel);
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
    }

    @Override
    public void onClickItemDeactive(RoomModel roomModel) {
        String message;

        if(roomModel.state == 1){
            message = "dar de baja";
        }else {
            message = "habilitar";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmación");
        builder.setMessage("¿Estás seguro que desea "+message+" este elemento?");

        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(roomModel.state == 1){
                    roomModel.state = 0;
                }else {
                    roomModel.state = 1;
                }
                _roomViewModel.updateRoom(roomModel);
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

    @Override
    public void onClickItemInfo(RoomModel roomModel) {

    }
}