package com.myapplication.view;

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

import java.util.List;

public class TenantAdapter extends RecyclerView.Adapter<TenantAdapter.TenantViewHolder> {
    private static List<Tenant> tenantList;

    public TenantAdapter(List<Tenant> tenantList) {
        this.tenantList = tenantList;
    }

    public static class TenantViewHolder extends RecyclerView.ViewHolder {
        TextView viewTnFirstName, viewTnLastName, viewTnPhone, viewTnEmail, viewTnType;

        public TenantViewHolder(@NonNull View itemView) {
            super(itemView);
            viewTnFirstName = itemView.findViewById(R.id.viewTnFirstName);
            viewTnLastName = itemView.findViewById(R.id.viewTnLastName);
            viewTnPhone = itemView.findViewById(R.id.viewTnPhone);
            viewTnEmail = itemView.findViewById(R.id.viewTnEmail);
            viewTnType = itemView.findViewById(R.id.viewTnType);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, TenantViewActivity.class);
                    intent.putExtra("TnID", tenantList.get(getAdapterPosition()).getId()); //envia correctamente el id
                    context.startActivity(intent);

//                    Toast.makeText(context, "Valor: " + tenantList.get(getAdapterPosition()).getId(), Toast.LENGTH_LONG).show();
                }
            });

        }
    }



    @NonNull
    @Override
    public TenantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tenant_list_item, null, false);
        return new TenantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TenantViewHolder holder, int position) {
        Tenant tenant = tenantList.get(position);
        holder.viewTnFirstName.setText(tenant.getFirstName());
        holder.viewTnLastName.setText(tenant.getLastName());
        holder.viewTnPhone.setText(tenant.getPhone());
        holder.viewTnEmail.setText(tenant.getEmail());
        holder.viewTnType.setText(tenant.getType());
    }

    @Override
    public int getItemCount() {
        return tenantList.size();
    }
}
