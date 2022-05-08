package com.example.firstproject.tradepoints;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstproject.DBHelper;
import com.example.firstproject.R;
import com.example.firstproject.entities.TradePoint;

import java.util.ArrayList;

public class TradePointsAdapter extends RecyclerView.Adapter<TradePointsViewHolder> {

    private Context context;
    private ArrayList<TradePoint> allTradePoints;
    private DBHelper dbHelper;

    public TradePointsAdapter(Context context, DBHelper db) {
        this.context = context;
        this.allTradePoints = db.listTradePoints();
        dbHelper = db;
    }

    @Override
    public TradePointsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trade_points_list_layout, parent, false);
        return new TradePointsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TradePointsViewHolder holder, int position) {
        final TradePoint tradePoint = allTradePoints.get(position);

        //creating card of trade point for list in recycler view
        holder.name.setText(tradePoint.getName());
        holder.address.setText(tradePoint.getAddress());

        holder.edit.setOnClickListener(view -> editTaskDialog(tradePoint));

        holder.delete.setOnClickListener(view -> {

            dbHelper.deleteTradePoint(tradePoint.getId());

            ((Activity)context).finish();
            context.startActivity(((Activity) context).getIntent());
        });
    }

    @Override
    public int getItemCount() {
        return allTradePoints.size();
    }


    private void editTaskDialog(final TradePoint tradePoint){
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.add_trade_point_layout, null);

        final EditText nameField = subView.findViewById(R.id.enter_trade_point_name);
        final EditText addressField = subView.findViewById(R.id.enter_trade_point_address);

        //filling trade points data in cards
        if(tradePoint != null){
            nameField.setText(tradePoint.getName());
            addressField.setText(tradePoint.getAddress());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Изменить торговую точку");
        builder.setView(subView);
        builder.create();

        //creating change button functionality
        builder.setPositiveButton("ИЗМЕНИТЬ", (dialog, which) -> {
            final String name = nameField.getText().toString();
            final String address = addressField.getText().toString();

            if(TextUtils.isEmpty(name) || TextUtils.isEmpty(address)){
                Toast.makeText(context, "Проверьте введённые данные", Toast.LENGTH_LONG).show();
            } else {
                dbHelper.updateTradePoint(new TradePoint(tradePoint.getId(), name, address));

                //refreshing trade points screen for update list of trade points recycler view
                ((Activity)context).finish();
                context.startActivity(((Activity)context).getIntent());
            }
        });

        //creating cancel button functionality
        builder.setNegativeButton("ОТМЕНА", (dialog, which) -> Toast.makeText(context, "Изменение отменено", Toast.LENGTH_LONG).show());
        builder.show();
    }
}
