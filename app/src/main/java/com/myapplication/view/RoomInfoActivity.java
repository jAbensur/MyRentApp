package com.myapplication.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.myapplication.databinding.ActivityInfoRoomBinding;
import com.myapplication.model.Room;

public class RoomInfoActivity extends AppCompatActivity {

    private ActivityInfoRoomBinding binding;
    private Room room;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInfoRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getIntent().hasExtra("model")){
            room = getIntent().getParcelableExtra("model");
            binding.txtRName.setText(room.nameR);
            binding.txtNumber.setText(String.valueOf(room.number));
            binding.txtDescription.setText(room.description);
            binding.txtMaterialType.setText(room.materialType);

            getSupportActionBar().setTitle(room.nameR);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp(){
        getOnBackPressedDispatcher().onBackPressed();
        return  super.onSupportNavigateUp();
    }
}