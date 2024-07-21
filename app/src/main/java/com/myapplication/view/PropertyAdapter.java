package com.myapplication.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.myapplication.R;
import com.myapplication.model.Property;
import com.myapplication.viewmodel.PropertyViewModel;

import java.util.List;

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder> {
    List<Property> propertyList;
    private Context context;
    private PropertyViewModel propertyViewModel;

    public PropertyAdapter(Context context, List<Property> propertyList, PropertyViewModel propertyViewModel) {
        this.context = context;
        this.propertyList = propertyList;
        this.propertyViewModel = propertyViewModel;
    }

    public static class PropertyViewHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvDescription, tvAddress, tvState;
        ImageView ivDelete, ivEdit, ivDisabled;
        public PropertyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.txtPName);
            tvDescription = itemView.findViewById(R.id.txtDescription);
            tvAddress = itemView.findViewById(R.id.txtAddress);
            tvState = itemView.findViewById(R.id.txtState);
            ivDelete = itemView.findViewById(R.id.imgDelete);
            ivEdit = itemView.findViewById(R.id.imgEdit);
            ivDisabled = itemView.findViewById(R.id.imgDisabled);
        }
    }

    @NonNull
    @Override
    public PropertyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.property_item_layout, parent, false);
        return new PropertyAdapter.PropertyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyViewHolder holder, int position) {
        Property property = propertyList.get(position);
        holder.tvName.setText(property.nameP);
        holder.tvDescription.setText(property.description);
        holder.tvAddress.setText(property.address);
        String state = property.state == 1 ? "Activo" : "Inactivo";
        holder.tvState.setText(state);

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirmación");
                builder.setMessage("¿Estás seguro que deseas eliminar este elemento?");

                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        propertyViewModel.deleteProperty(property);
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
        });
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PropertyAddActivity.class);
                intent.putExtra("model",property);
                context.startActivity(intent);
            }
        });
        holder.ivDisabled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message;

                if(property.state == 1){
                    message = "dar de baja";
                }else {
                    message = "habilitar";
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirmación");
                builder.setMessage("¿Estás seguro que desea "+message+" este elemento?");

                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(property.state == 1){
                            property.state = 0;
                        }else {
                            property.state = 1;
                        }
                        propertyViewModel.updateProperty(property);
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
        });
    }

    @Override
    public int getItemCount() {
        return propertyList.size();
    }
}
