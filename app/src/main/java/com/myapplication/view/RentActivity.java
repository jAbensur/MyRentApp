package com.myapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myapplication.model.Rent;
import com.myapplication.R;
import java.util.ArrayList;
import java.util.List;

public class RentActivity extends AppCompatActivity {
    private EditText etSearch;
    private Button btnSearch;
    private RecyclerView rvRentals;
    private FloatingActionButton btnAdd;
    private RentAdapter rentalAdapter;
    private List<Rent> rentalList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);

        etSearch = findViewById(R.id.etSearch);
        btnSearch = findViewById(R.id.btnSearch);
        rvRentals = findViewById(R.id.rvRentals);
        btnAdd = findViewById(R.id.btnAdd);

        rentalList = new ArrayList<>();
        rentalAdapter = new RentAdapter(rentalList, this);
        rvRentals.setLayoutManager(new LinearLayoutManager(this));
        rvRentals.setAdapter(rentalAdapter);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchRentals(etSearch.getText().toString());
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewRental();
            }
        });

        loadRentals();
    }
    private void searchRentals(String query) {
        // Implementar lógica de búsqueda
    }
    private void addNewRental() {
        Intent intent = new Intent(this, RentCreateActivity.class);
        startActivity(intent);
    }
    private void loadRentals() {
        // Implementar lógica para cargar los registros iniciales
    }
}