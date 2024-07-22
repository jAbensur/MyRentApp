package com.myapplication.view;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import com.myapplication.model.Chamber;
import com.myapplication.model.Rent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.myapplication.R;
import com.myapplication.model.Tenant;
import com.myapplication.viewmodel.ChamberViewModel;
import com.myapplication.viewmodel.RentViewModel;
import com.myapplication.viewmodel.TenantViewModel;

public class RentAdapter extends RecyclerView.Adapter<RentAdapter.RentViewHolder>{
    private List<Rent> rentList;
    private Context context;
    private RentViewModel rentViewModel;
    private TenantViewModel tenantViewModel;
    private ChamberViewModel chamberViewModel;

    public RentAdapter(Context context,List<Rent> rentList, RentViewModel rentViewModel,
                       TenantViewModel tenantViewModel, ChamberViewModel chamberViewModel) {
        this.context = context;
        this.rentList = rentList;
        this.rentViewModel = rentViewModel;
        this.tenantViewModel = tenantViewModel;
        this.chamberViewModel = chamberViewModel;
    }

    public static class RentViewHolder extends RecyclerView.ViewHolder {
        TextView tvStartDate, tvEndDate, tvPrice, tvTenant, tvChamber;
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

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler mainHandler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            LiveData<Tenant> tenantLiveData = tenantViewModel.getTenantById(rent.getTenantId());
            mainHandler.post(() -> {
                tenantLiveData.observeForever(new Observer<Tenant>() {
                    @Override
                    public void onChanged(Tenant tenant) {
                        if (tenant != null) {
                            holder.tvTenant.setText(tenant.getDni());
                        } else {
                            holder.tvTenant.setText("N/A");
                        }
                    }
                });
            });

            LiveData<Chamber> chamberLiveData = chamberViewModel.getChamberById(rent.getChamberId());
            mainHandler.post(() -> {
                chamberLiveData.observeForever(new Observer<Chamber>() {
                    @Override
                    public void onChanged(Chamber chamber) {
                        if (chamber != null) {
                            holder.tvChamber.setText(chamber.getName());
                        } else {
                            holder.tvChamber.setText("N/A");
                        }
                    }
                });
            });
        });

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
