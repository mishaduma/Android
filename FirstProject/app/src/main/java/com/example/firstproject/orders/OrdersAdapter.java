package com.example.firstproject.orders;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstproject.DBHelper;
import com.example.firstproject.R;
import com.example.firstproject.entities.Good;
import com.example.firstproject.entities.Order;
import com.example.firstproject.entities.TradePoint;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersViewHolder> {

    private Context context;
    private ArrayList<Order> allOrders;
    private ArrayList<Good> allGoods;
    private ArrayList<TradePoint> allTradePoints;
    private DBHelper dbHelper;

    public OrdersAdapter(Context context, DBHelper db) {
        this.context = context;
        this.allOrders = db.listOrders();
        this.allGoods = db.listGoods();
        this.allTradePoints = db.listTradePoints();
        dbHelper = db;
    }

    @Override
    public OrdersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_list_layout, parent, false);
        return new OrdersViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(OrdersViewHolder holder, int position) {
        final Order order = allOrders.get(position);

        //creating card of order for list in recycler view
        Good good = allGoods.stream().filter(good1 -> String.valueOf(good1.getId()).equals(order.getGoodId()))
                .collect(Collectors.toList()).get(0);
        TradePoint tradePoint = allTradePoints.stream().filter(tradePoint1 -> String.valueOf(tradePoint1.getId())
                .equals(order.getTradePointId())).collect(Collectors.toList()).get(0);

        holder.goodName.setText(good.getName());
        holder.tradePointName.setText(tradePoint.getName());
        holder.count.setText(String.valueOf(order.getCount()) + " " + good.getMeasure());
        holder.fullPrice.setText(String.valueOf(order.getFullPrice()) + " \u20BD");

        holder.delete.setOnClickListener(view -> {

            dbHelper.deleteOrder(order.getId());

            //refreshing orders screen for update list of orders in recycler view
            ((Activity)context).finish();
            context.startActivity(((Activity) context).getIntent());
        });
    }

    @Override
    public int getItemCount() {
        return allOrders.size();
    }
}
