package com.example.firstproject.tradepoints;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstproject.R;

public class TradePointsViewHolder extends RecyclerView.ViewHolder {

    public TextView name, address;
    public ImageView edit, delete;

    public TradePointsViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.trade_point_name);
        address = itemView.findViewById(R.id.trade_point_address);
        edit = itemView.findViewById(R.id.edit_trade_point);
        delete = itemView.findViewById(R.id.delete_trade_point);
    }
}
