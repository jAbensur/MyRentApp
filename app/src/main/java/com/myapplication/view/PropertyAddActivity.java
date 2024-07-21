package com.myapplication.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.textfield.TextInputEditText;
import com.myapplication.R;
import com.myapplication.model.Property;
import com.myapplication.viewmodel.PropertyViewModel;

public class PropertyAddActivity extends AppCompatActivity {
    private PropertyViewModel propertyViewModel;
    private Property property;
    private boolean _isEdit = false;
    private Button btnAdd;
    private TextInputEditText edtProperty, edtDescription, edtAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);

        edtProperty = findViewById(R.id.edtProperty);
        edtDescription = findViewById(R.id.edtDescription);
        edtAddress = findViewById(R.id.edtAddress);
        btnAdd = findViewById(R.id.btnAddProperty);

        propertyViewModel =  new ViewModelProvider(this).get(PropertyViewModel.class);
        if (getIntent().hasExtra("model"))
        {
            property = getIntent().getParcelableExtra("model");
            edtProperty.setText(property.nameP);
            edtDescription.setText(property.description);
            edtAddress.setText(property.address);
            _isEdit = true;
        }

        if (_isEdit)
        {
            getSupportActionBar().setTitle("Editar Propiedad");
        }else {
            getSupportActionBar().setTitle("Nueva Propiedad");

        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (_isEdit){
                    property.nameP = edtProperty.getText().toString();
                    property.description = edtDescription.getText().toString();
                    property.address = edtAddress.getText().toString();
                    property.state = 1;
                    propertyViewModel.updateProperty(property);
                }else{
                    property = new Property();
                    property.nameP = edtProperty.getText().toString();
                    property.description = edtDescription.getText().toString();
                    property.address = edtAddress.getText().toString();
                    property.state = 1;
                    propertyViewModel.insertProperty(property);
                }
                finish();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        getOnBackPressedDispatcher().onBackPressed();
        return  super.onSupportNavigateUp();
    }

}