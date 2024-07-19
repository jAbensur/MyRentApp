package com.myapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myapplication.model.Rent;
import com.myapplication.R;
import com.myapplication.viewmodel.RentViewModel;
import androidx.lifecycle.Observer;
import java.util.List;

public class RentActivity extends AppCompatActivity {
    private EditText etSearch;
    private Button btnSearch;
    private RecyclerView recyclerView;
    private FloatingActionButton btnAdd, btnBack;
    private RentAdapter rentAdapter;
    private List<Rent> rentalList;
    private RentViewModel rentViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);

        recyclerView = findViewById(R.id.rvRentals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        etSearch = findViewById(R.id.etSearch);
        btnSearch = findViewById(R.id.btnSearch);
        btnAdd = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBack);

        rentViewModel =  new ViewModelProvider(this).get(RentViewModel.class);

        rentViewModel.getAllRents().observe(this, new Observer<List<Rent>>() {
            @Override
            public void onChanged(List<Rent> rents) {
                rentAdapter = new RentAdapter(RentActivity.this,rents,rentViewModel);
                recyclerView.setAdapter(rentAdapter);
            }
        });

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

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
    private void message(String text){
        Toast.makeText(RentActivity.this, text, Toast.LENGTH_LONG).show();
    }
}