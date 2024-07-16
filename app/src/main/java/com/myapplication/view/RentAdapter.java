package com.myapplication.view;

import android.content.Context;
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

public class RentAdapter extends RecyclerView.Adapter<RentAdapter.RentViewHolder>{
    private List<Rent> rentList;
    private Context context;
    public RentAdapter(List<Rent> rentalList, Context context) {
        this.rentList = rentalList;
        this.context = context;
    }
    @NonNull
    @Override
    public RentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rent_item, parent, false);
        return new RentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RentViewHolder holder, int position) {
        Rent rent = rentList.get(position);
        holder.tvRentalInfo.setText(rent.toString()); // Ajustar según la información de tu clase Rental

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implementar lógica para editar el registro
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implementar lógica para eliminar el registro
            }
        });
    }

    @Override
    public int getItemCount() {
        return rentList.size();
    }

    public static class RentViewHolder extends RecyclerView.ViewHolder {
        TextView tvRentalInfo;
        Button btnEdit, btnDelete;

        public RentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRentalInfo = itemView.findViewById(R.id.tvRentalInfo);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
