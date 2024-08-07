package com.myapplication.view;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.myapplication.R;
import com.myapplication.databinding.ActivityAddRoomBinding;
import com.myapplication.model.Property;
import com.myapplication.model.Room;
import com.myapplication.viewmodel.PropertyViewModel;
import com.myapplication.viewmodel.RoomViewModel;

import java.util.ArrayList;
import java.util.List;

public class RoomAddActivity extends AppCompatActivity {

    private ActivityAddRoomBinding binding;
    private PropertyViewModel propertyViewModel;
    private RoomViewModel roomViewModel;
    private Room room;
    private boolean isEdit = false;
    private Button btnAdd;
    private TextInputEditText edtNameRoom, edtNumberRoom, edtCapacity, edtDescription, edtPrice;
    public String[] roomMaterials = {"Madera","Tripley","Ladrillo"};
    private String roomMaterial;
    private List<Property> propertyList = new ArrayList<>();
    private ArrayAdapter<String> selectPropertyAdapter;
    private AutoCompleteTextView selectPropertyAutocomplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        edtNameRoom = findViewById(R.id.edtNameR);
        edtNumberRoom = findViewById(R.id.edtRoomNumber);
        edtCapacity = findViewById(R.id.edtCapacity);
        edtDescription = findViewById(R.id.edtDescription);
        edtPrice = findViewById(R.id.edtRoomPrice);
        btnAdd = findViewById(R.id.btnAddRoom);

        selectPropertyAutocomplete = findViewById(R.id.edtProperty);

        selectPropertyAdapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, new ArrayList<>());
        selectPropertyAutocomplete.setAdapter(selectPropertyAdapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        propertyViewModel = new ViewModelProvider(this).get(PropertyViewModel.class);
        roomViewModel = new ViewModelProvider(this).get(RoomViewModel.class);

        if (getIntent().hasExtra("model")){
            room = getIntent().getParcelableExtra("model");
            edtNameRoom.setText(room.nameR);
            edtNumberRoom.setText(String.valueOf(room.number));
            edtCapacity.setText(String.valueOf(room.capacity));
            edtDescription.setText(room.description);
            edtPrice.setText(String.valueOf(room.pricePerMonth));
            isEdit=true;
        }

        initDropDown();

        propertyViewModel.getAllPropertiesLive().observe(this, new Observer<List<Property>>() {
            @Override
            public void onChanged(List<Property> properties) {
                propertyList = properties;
                List<String> propertyNames = new ArrayList<>();
                for (Property property : properties){
                    propertyNames.add(property.getNameP());
                }
                selectPropertyAdapter.clear();
                selectPropertyAdapter.addAll(propertyNames);
                selectPropertyAdapter.notifyDataSetChanged();

                if(room != null){
                    setDefaultProperty(room.propertyId);
                }
            }
        });

        if (isEdit)
        {
            getSupportActionBar().setTitle("Editar Habitacion");
        }else {
            getSupportActionBar().setTitle("Nueva Habitacion");
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Property property = selectedProperty(selectPropertyAutocomplete);

                int propertyId = 0;

                if(property != null){
                    propertyId = property.getId();
                }

                if(propertyId == 0){
                    Toast.makeText(RoomAddActivity.this,"Por favor ingrese una propiedad", Toast.LENGTH_LONG).show();
                    return;
                }

                if (isEdit){
                    room.nameR= edtNameRoom.getText().toString().trim();
                    room.number =  Integer.parseInt(edtNumberRoom.getText().toString().trim());
                    room.capacity = Integer.parseInt(edtCapacity.getText().toString().trim());
                    room.description = edtDescription.getText().toString().trim();
                    room.materialType = roomMaterial;
                    room.pricePerMonth = Double.parseDouble(edtPrice.getText().toString().trim());
                    room.propertyId = propertyId;

                    roomViewModel.updateRoom(room);
                }else{

                    room = new Room();
                    room.nameR= edtNameRoom.getText().toString().trim();
                    room.number =  Integer.parseInt(edtNumberRoom.getText().toString().trim());
                    room.capacity = Integer.parseInt(edtCapacity.getText().toString().trim());
                    room.description = edtDescription.getText().toString().trim();
                    room.materialType = roomMaterial;
                    room.pricePerMonth = Double.parseDouble(edtPrice.getText().toString().trim());
                    room.available = 1;
                    room.state = 1;
                    room.propertyId = propertyId;

                    roomViewModel.insertRoom(room);
                }
                finish();

            }
        });
    }

    private void initDropDown(){
        ArrayAdapter<String> arrayAdapter =new ArrayAdapter<>(this, android.R.layout.select_dialog_item, roomMaterials);
        binding.edtRoomMaterial.setAdapter(arrayAdapter);

        if(room != null && room.getMaterialType() != null){
            int position = arrayAdapter.getPosition(room.getMaterialType());

            if (position >= 0){
                binding.edtRoomMaterial.setText(room.getMaterialType(), false);
            }
        }

        binding.edtRoomMaterial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                roomMaterial = (String) adapterView.getItemAtPosition(position);
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp(){
        getOnBackPressedDispatcher().onBackPressed();
        return  super.onSupportNavigateUp();
    }

    private Property selectedProperty(AutoCompleteTextView propertyGet){
        String selectPropertyName = propertyGet.getText().toString();
        Property selectProperty = null;
        for(Property property : propertyList){
            if (property.getNameP().equals(selectPropertyName)){
                selectProperty = property;
                break;
            }
        }
        return selectProperty;
    }

    private void setDefaultProperty(int propertyId) {
        for (Property property : propertyList) {
            if (property.getId() == propertyId) {
                selectPropertyAutocomplete.setText(property.getNameP(), false);
                break;
            }
        }
    }
}