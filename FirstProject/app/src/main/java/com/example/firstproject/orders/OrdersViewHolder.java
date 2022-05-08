package com.example.firstproject.orders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstproject.R;

public class OrdersViewHolder extends RecyclerView.ViewHolder {

    public TextView goodName, tradePointName, count, fullPrice;
    public ImageView delete;

    public OrdersViewHolder(@NonNull View itemView) {
        super(itemView);
        goodName = itemView.findViewById(R.id.order_good_name);
        tradePointName = itemView.findViewById(R.id.order_trade_point_name);
        count = itemView.findViewById(R.id.count_measure);
        fullPrice = itemView.findViewById(R.id.full_price);
        delete =itemView.findViewById(R.id.delete_order);
    }
}
