package com.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapplication.R;
import com.myapplication.model.Tenant;
import com.myapplication.ViewTenantActivity;

import java.util.ArrayList;

public class ListTenantAdapter extends RecyclerView.Adapter<ListTenantAdapter.TenantViewHolder> {
    // Class designed to work with a RecyclerView, Allows recycling of views

    ArrayList<Tenant> tenantList;

    public ListTenantAdapter(ArrayList<Tenant> tenantList) {
        this.tenantList = tenantList;
    }

    @NonNull
    @Override
    public TenantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_tenant, null, false);
        return new TenantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TenantViewHolder holder, int position) {
//        binds the data to the views (TextViews) in each item of the RecyclerView
        holder.viewTnFirstName.setText(tenantList.get(position).getTnFirstName());
        holder.viewTnLastName.setText(tenantList.get(position).getTnLastName());
        holder.viewTnPhone.setText(tenantList.get(position).getTnPhone());
        holder.viewTnEmail.setText(tenantList.get(position).getTnEmail());
        holder.viewTnType.setText(tenantList.get(position).getTnType());
    }

    @Override
    public int getItemCount() {
        return tenantList.size();
    }

    public class TenantViewHolder extends RecyclerView.ViewHolder {

        //  Assigns views from layout to individual template
        TextView viewTnFirstName, viewTnLastName, viewTnPhone, viewTnEmail, viewTnType;

        public TenantViewHolder(@NonNull View itemView) {
            super(itemView);
            viewTnFirstName = itemView.findViewById(R.id.viewTnFirstName);
            viewTnLastName = itemView.findViewById(R.id.viewTnLastName);
            viewTnPhone = itemView.findViewById(R.id.viewTnPhone);
            viewTnEmail = itemView.findViewById(R.id.viewTnEmail);
            viewTnType = itemView.findViewById(R.id.viewTnType);

//            when you click on any of the elements
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ViewTenantActivity.class);
                    intent.putExtra("TnID", tenantList.get(getAdapterPosition()).getTnID());

                    context.startActivity(intent);
                }
            });

        }
    }
}
