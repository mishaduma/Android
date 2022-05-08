package com.example.firstproject.goods;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstproject.R;

public class GoodsViewHolder extends RecyclerView.ViewHolder {

    public TextView name, measure, price;
    public ImageView edit, delete;

    public GoodsViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.good_name);
        measure = itemView.findViewById(R.id.good_measure);
        price = itemView.findViewById(R.id.good_price);
        edit = itemView.findViewById(R.id.edit_good);
        delete = itemView.findViewById(R.id.delete_good);
    }
}
