package com.myapplication.view;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.myapplication.R;
import com.myapplication.databinding.ActivityAddRoomBinding;
import com.myapplication.model.RoomModel;
import com.myapplication.viewmodel.RoomViewModel;

public class AddRoomActivity extends AppCompatActivity {

    private ActivityAddRoomBinding _binding;
    private String _nameRoom, _description, _roomMaterial;
    private int _numberRoom , _capacity, state, available;
    private double _price;
    private RoomViewModel _roomViewModel;
    private RoomModel _rooModel;
    private String[] materials = {"Madera", "Tripley", "Ladrillo"};
    private boolean _isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _binding = ActivityAddRoomBinding.inflate(getLayoutInflater());
        setContentView(_binding.getRoot());
        initDropDown();
        _roomViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(RoomViewModel.class);

        if (getIntent().hasExtra("model")){
            _rooModel = getIntent().getParcelableExtra("model");
            _binding.edtNameR.setText(_rooModel.nameR);
            _binding.edtRoomNumber.setText(_rooModel.number);
            _binding.edtDescription.setText(_rooModel.description);
            _binding.edtRoomPrice.setText(String.valueOf(_rooModel.pricePerMonth));
            _isEdit=true;

        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        getOnBackPressedDispatcher().onBackPressed();
        return  super.onSupportNavigateUp();
    }

    private void initDropDown(){
        ArrayAdapter<String> arrayAdapter =new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, materials);
        _binding.edtRoomMaterial.setAdapter(arrayAdapter);
        _binding.edtRoomMaterial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                _roomMaterial = (String)adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}