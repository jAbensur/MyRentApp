package com.myapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.myapplication.databinding.ActivityInfoRoomBinding;
import com.myapplication.model.Room;
import com.myapplication.viewmodel.RoomViewModel;

public class InfoRoomActivity extends AppCompatActivity {

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