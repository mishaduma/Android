package com.example.firstproject.goods;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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
import com.example.firstproject.entities.Good;

import java.util.ArrayList;

public class GoodsAdapter extends RecyclerView.Adapter<GoodsViewHolder> {

    private Context context;
    private ArrayList<Good> listGoods;
    private DBHelper dbHelper;

    public GoodsAdapter(Context context, DBHelper db) {
        this.context = context;
        this.listGoods = db.listGoods();
        dbHelper = db;
    }

    @Override
    public GoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_list_layout, parent, false);
        return new GoodsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GoodsViewHolder holder, int position) {
        final Good good = listGoods.get(position);

        //creating card of good for list in recycler view
        holder.name.setText(good.getName());
        holder.measure.setText(good.getMeasure());
        holder.price.setText(good.getPrice() + " \u20BD");

        holder.edit.setOnClickListener(view -> editTaskDialog(good));

        holder.delete.setOnClickListener(view -> {

            dbHelper.deleteGood(good.getId());

            ((Activity)context).finish();
            context.startActivity(((Activity) context).getIntent());
        });
    }

    @Override
    public int getItemCount() {
        return listGoods.size();
    }


    @SuppressLint("ResourceAsColor")
    private void editTaskDialog(final Good goods){
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.add_good_layout, null);

        final EditText nameField = subView.findViewById(R.id.enter_good_name);
        final EditText measureField = subView.findViewById(R.id.enter_good_measure);
        final EditText priceField = subView.findViewById(R.id.enter_good_price);

        //filling goods data in cards
        if(goods != null){
            nameField.setText(goods.getName());
            measureField.setText(goods.getMeasure());
            priceField.setText(String.valueOf(goods.getPrice()));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Изменить товар");
        builder.setView(subView);
        builder.create();

        //creating change button functionality
        builder.setPositiveButton("ИЗМЕНИТЬ", (dialog, which) -> {
            final String name = nameField.getText().toString();
            final String measure = measureField.getText().toString();
            final double price = Double.parseDouble(priceField.getText().toString());

            if(TextUtils.isEmpty(name) || TextUtils.isEmpty(measure) || TextUtils.isEmpty(String.valueOf(price))){
                Toast.makeText(context, "Проверьте введённые данные", Toast.LENGTH_LONG).show();
            }
            else{
                dbHelper.updateGood(new Good(goods.getId(), name, measure, price));

                //refreshing goods screen for update list of goods in recycler view
                ((Activity)context).finish();
                context.startActivity(((Activity)context).getIntent());
            }
        });

        //creating cancel button functionality
        builder.setNegativeButton("ОТМЕНА", (dialog, which) -> Toast.makeText(context, "Изменение отменено", Toast.LENGTH_LONG).show());
        builder.show();
    }
}
