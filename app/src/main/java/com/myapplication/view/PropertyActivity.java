package com.myapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myapplication.R;
import com.myapplication.model.Property;
import com.myapplication.viewmodel.PropertyViewModel;
import java.util.List;

public class PropertyActivity extends AppCompatActivity {

    private PropertyViewModel propertyViewModel;
    private PropertyAdapter propertyAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property);

        recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FloatingActionButton addPropertyButton = findViewById(R.id.addProperty);

        getSupportActionBar().setTitle("Propiedades");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        propertyViewModel =  new ViewModelProvider(this).get(PropertyViewModel.class);

        propertyViewModel.getAllPropertiesLive().observe(PropertyActivity.this, new Observer<List<Property>>() {
            @Override
            public void onChanged(List<Property> propertyModelList) {
                if(propertyModelList != null)
                {
                    propertyAdapter = new PropertyAdapter(PropertyActivity.this,propertyModelList, propertyViewModel);
                    recyclerView.setAdapter(propertyAdapter);
                }
            }
        });
        addPropertyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PropertyActivity.this, AddPropertyActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        getOnBackPressedDispatcher().onBackPressed();
        return super.onSupportNavigateUp();
    }
}