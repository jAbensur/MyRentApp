package com.myapplication.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.myapplication.adapter.PropertyAdapter;
import com.myapplication.databinding.ActivityPropertyBinding;
import com.myapplication.model.PropertyModel;
import com.myapplication.onClickInterface.OnClickItemInterfaceProperty;
import com.myapplication.viewmodel.PropertyViewModel;

import java.util.List;

public class PropertyActivity extends AppCompatActivity implements OnClickItemInterfaceProperty {

    private ActivityPropertyBinding _binding;
    private PropertyViewModel _propertyViewModel;
    private PropertyAdapter _adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _binding= ActivityPropertyBinding.inflate(getLayoutInflater());
        setContentView(_binding.getRoot());
        _binding.RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        _adapter = new PropertyAdapter(this);
        _binding.RecyclerView.setAdapter(_adapter);

        getSupportActionBar().setTitle("Propiedades");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        _binding.addProperty.setOnClickListener(view ->{
            startActivity(new Intent(PropertyActivity.this, AddPropertyActivity.class));
        });

        _propertyViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(PropertyViewModel.class);

        _propertyViewModel.getAllPropertiesLive().observe(PropertyActivity.this, new Observer<List<PropertyModel>>() {
            @Override
            public void onChanged(List<PropertyModel> propertyModelList) {
                if(propertyModelList != null)
                {
                    _adapter.setProperties(propertyModelList);
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        getOnBackPressedDispatcher().onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onClickItem(PropertyModel propertyModel, boolean isEdit) {

        if (isEdit)
        {
            Intent intent = new Intent(PropertyActivity.this, AddPropertyActivity.class);
            intent.putExtra("model",propertyModel);
            startActivity(intent);
        }else{

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirmación");
            builder.setMessage("¿Estás seguro que deseas eliminar este elemento?");

            builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    _propertyViewModel.deleteProperty(propertyModel);
                    dialog.dismiss();
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    @Override
    public void onClickItemDeactive(PropertyModel propertyModel) {

        String message;

        if(propertyModel.state == 1){
            message = "dar de baja";
        }else {
            message = "habilitar";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmación");
        builder.setMessage("¿Estás seguro que desea "+message+" este elemento?");

        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(propertyModel.state == 1){
                    propertyModel.state = 0;
                }else {
                    propertyModel.state = 1;
                }
                _propertyViewModel.updateProperty(propertyModel);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}