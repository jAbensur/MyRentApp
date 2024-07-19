package com.myapplication.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.myapplication.databinding.ActivityAddPropertyBinding;
import com.myapplication.model.PropertyModel;
import com.myapplication.viewmodel.PropertyViewModel;

public class AddPropertyActivity extends AppCompatActivity {

    private ActivityAddPropertyBinding _binding;
    private String _property, _description, _address;
    private int _state;
    private PropertyViewModel _propertyViewModel;
    private PropertyModel _propertyModel;
    private boolean _isEdit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _binding = ActivityAddPropertyBinding.inflate(getLayoutInflater());
        setContentView(_binding.getRoot());
        _propertyViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(PropertyViewModel.class);

        if (getIntent().hasExtra("model"))
        {
            _propertyModel = getIntent().getParcelableExtra("model");
            _binding.edtProperty.setText(_propertyModel.nameP);
            _binding.edtDescription.setText(_propertyModel.description);
            _binding.edtAddress.setText(_propertyModel.address);
            _isEdit = true;
        }

        if (_isEdit)
        {
            getSupportActionBar().setTitle("Editar Propiedad");
        }else {
            getSupportActionBar().setTitle("Nueva Propiedad");

        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        _binding.btnAddProperty.setOnClickListener(view -> {

            _property = _binding.edtProperty.getText().toString().trim();
            _description = _binding.edtDescription.getText().toString().trim();
            _address = _binding.edtAddress.getText().toString().trim();

            if (_isEdit){
                _propertyModel.nameP = _property;
                _propertyModel.description = _description;
                _propertyModel.address = _address;

                _propertyViewModel.updateProperty(_propertyModel);

                Toast.makeText(this, "UPDATED", Toast.LENGTH_SHORT).show();
            }else{
                _propertyModel = new PropertyModel();
                _propertyModel.nameP = _property;
                _propertyModel.description = _description;
                _propertyModel.address = _address;
                _propertyModel.state = 1;

                _propertyViewModel.insertProperty(_propertyModel);
                Toast.makeText(this, "INSERTED", Toast.LENGTH_SHORT).show();
            }
            finish();
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        getOnBackPressedDispatcher().onBackPressed();
        return  super.onSupportNavigateUp();
    }

}