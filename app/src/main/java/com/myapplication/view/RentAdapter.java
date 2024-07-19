package com.myapplication.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.myapplication.model.Rent;
import java.util.List;
import com.myapplication.R;
import com.myapplication.viewmodel.RentViewModel;

public class RentAdapter extends RecyclerView.Adapter<RentAdapter.RentViewHolder>{
    private List<Rent> rentList;
    private Context context;
    private RentViewModel rentViewModel;
    public static class RentViewHolder extends RecyclerView.ViewHolder {
        TextView tvStartDate;
        TextView tvEndDate;
        TextView tvPrice;
        TextView tvTenant;
        TextView tvChamber;
        Button btnEdit, btnDelete;
        public RentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStartDate = itemView.findViewById(R.id.tvStartDate);
            tvEndDate = itemView.findViewById(R.id.tvEndDate);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvTenant = itemView.findViewById(R.id.tvTenant);
            tvChamber = itemView.findViewById(R.id.tvChamber);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
    public RentAdapter(Context context,List<Rent> rentList, RentViewModel rentViewModel) {
        this.context = context;
        this.rentList = rentList;
        this.rentViewModel = rentViewModel;
    }
    @NonNull
    @Override
    public RentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rent_item, parent, false);
        return new RentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RentViewHolder holder, int position) {
        Rent rent = rentList.get(position);
        holder.tvStartDate.setText(rent.getStartDate());
        holder.tvEndDate.setText(rent.getEndDate());
        holder.tvPrice.setText(String.valueOf(rent.getPrice()));
        holder.tvTenant.setText(String.valueOf(rent.getTenantId()));
        holder.tvChamber.setText(String.valueOf(rent.getChamberId()));

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RentUpdateActivity.class);
                intent.putExtra("id", rent.getId());
                context.startActivity(intent);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rent.setState(0);
                rentViewModel.update(rent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rentList.size();
    }
}
