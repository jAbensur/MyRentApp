package com.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.myapplication.R;
import com.myapplication.databinding.PropertyItemLayoutBinding;
import com.myapplication.model.PropertyModel;
import com.myapplication.onClickInterface.OnClickItemInterfaceProperty;

import java.util.List;


public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.ViewHolder> {

    List<PropertyModel> propertiesModelList;
    private final OnClickItemInterfaceProperty _onClickItemInterfaceProperty;

    public PropertyAdapter(OnClickItemInterfaceProperty _onClickItemInterfaceProperty) {
        this._onClickItemInterfaceProperty = _onClickItemInterfaceProperty;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PropertyItemLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.property_item_layout,
                parent,
                false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(propertiesModelList!=null){
            PropertyModel propertyModel = propertiesModelList.get(position);

            holder.binding.setPropertyModel(propertyModel);
            holder.binding.setListener(_onClickItemInterfaceProperty);
        }
    }

    @Override
    public int getItemCount() {

        if(propertiesModelList!=null)
            return propertiesModelList.size();
        else return 0;
    }

    public void setProperties(List<PropertyModel> properties)
    {
        propertiesModelList = properties;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        PropertyItemLayoutBinding binding;

        public ViewHolder(@NonNull PropertyItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
