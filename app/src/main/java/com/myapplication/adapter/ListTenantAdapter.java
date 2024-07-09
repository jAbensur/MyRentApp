package com.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapplication.R;
import com.myapplication.model.Tenant;
import com.myapplication.viewTenantActivity;

import java.util.ArrayList;

public class ListTenantAdapter extends RecyclerView.Adapter<ListTenantAdapter.TenantViewHolder> {
//    clase diseñada para trabajar con un RecyclerView
//    permite reciclar vistas

    ArrayList<Tenant> listTenant;

    public ListTenantAdapter(ArrayList<Tenant> listaContactos){
        this.listTenant = listaContactos;
    }

    @NonNull
    @Override
    public TenantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_tenant, null, false);
        return new TenantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TenantViewHolder holder, int position) {
        holder.viewTnFirstName.setText(listTenant.get(position).getTnFirstName());
        holder.viewTnLastName.setText(listTenant.get(position).getTnLastName());
        holder.viewTnPhone.setText(listTenant.get(position).getTnPhone());
        holder.viewTnEmail.setText(listTenant.get(position).getTnEmail());
        holder.viewTnType.setText(listTenant.get(position).getTnType());
    }

    @Override
    public int getItemCount() {
        return listTenant.size();
    }

    public class TenantViewHolder extends RecyclerView.ViewHolder {

//        asigna listas de nuestra vista a la pantilla individual

        TextView viewTnFirstName, viewTnLastName, viewTnPhone, viewTnEmail, viewTnType;

        public TenantViewHolder(@NonNull View itemView) {
            super(itemView);
            viewTnFirstName = itemView.findViewById(R.id.viewTnFirstName);
            viewTnLastName = itemView.findViewById(R.id.viewTnLastName);
            viewTnPhone = itemView.findViewById(R.id.viewTnPhone);
            viewTnEmail = itemView.findViewById(R.id.viewTnEmail);
            viewTnType = itemView.findViewById(R.id.viewTnType);


//            al dar click a uno de los item's
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, viewTenantActivity.class);
                    intent.putExtra("TnID", listTenant.get(getAdapterPosition()).getTnID());

                    context.startActivity(intent);
                }
            });

        }
    }
}