package com.myapplication.view;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myapplication.R;
import com.myapplication.model.Room;
import com.myapplication.viewmodel.RoomViewModel;

import java.util.List;

public class RoomActivity extends AppCompatActivity {

    private RoomViewModel _roomViewModel;
    private RoomAdapter _roomAdapter;
    private RecyclerView _recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        _recyclerView = findViewById(R.id.RecyclerViewRoom);
        _recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FloatingActionButton addRoomButton = findViewById(R.id.addRoom);

        getSupportActionBar().setTitle("Habitaciones");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        _roomViewModel = new ViewModelProvider(this).get(RoomViewModel.class);

        _roomViewModel.getAllRooms().observe(RoomActivity.this, new Observer<List<Room>>() {
            @Override
            public void onChanged(List<Room> rooms) {
                if (rooms != null){
                    _roomAdapter = new RoomAdapter(RoomActivity.this, rooms, _roomViewModel);
                    _recyclerView.setAdapter(_roomAdapter);
                }
            }
        });
    }
}