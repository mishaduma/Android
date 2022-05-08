package com.example.firstproject.orders;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstproject.DBHelper;
import com.example.firstproject.R;
import com.example.firstproject.entities.Good;
import com.example.firstproject.entities.Order;
import com.example.firstproject.entities.TradePoint;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private ArrayList<Order> allOrders = new ArrayList<>();
    private ArrayList<Good> allGoods = new ArrayList<>();
    private ArrayList<TradePoint> allTradePoints = new ArrayList<>();
    private OrdersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        //creating recycler view with orders cards
        RecyclerView orderView = findViewById(R.id.orders_list);
        orderView.setLayoutManager(new LinearLayoutManager(this));
        dbHelper = new DBHelper(this);
        allOrders = dbHelper.listOrders();
        allGoods = dbHelper.listGoods();
        allTradePoints = dbHelper.listTradePoints();

        if (allOrders.size() > 0) {
            orderView.setVisibility(View.VISIBLE);
            adapter = new OrdersAdapter(this, dbHelper);
            orderView.setAdapter(adapter);
        } else {
            orderView.setVisibility(View.GONE);
            Toast.makeText(this, "Заказов нет. Нужно добавить", Toast.LENGTH_LONG).show();
        }

        //setting onclick listener for button to launch alert dialog
        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(view -> addTaskDialog());
    }

    private void addTaskDialog(){

        View subView = LayoutInflater.from(this).inflate(R.layout.add_order_layout, null);

        final Spinner chooseGood = subView.findViewById(R.id.good_spinner);
        final Spinner chooseTradePoint = subView.findViewById(R.id.trade_point_spinner);
        final EditText countField = subView.findViewById(R.id.enter_count);
        final TextView measureField = subView.findViewById(R.id.measure);
        final TextView fullPriceField = subView.findViewById(R.id.full_price);

        //filling goods spinner
        List<String> goodNames = new ArrayList<>();
        for (Good allGood : allGoods) {
            String name = allGood.getName();
            goodNames.add(name);
        }

        ArrayAdapter<String> goodAdapter = new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, goodNames);
        chooseGood.setAdapter(goodAdapter);

        //filling trade points spinner
        List<String> tradePointNames = new ArrayList<>();
        for (TradePoint tradePoint : allTradePoints) {
            tradePointNames.add(tradePoint.getName());
        }

        ArrayAdapter<String> tradePointAdapter = new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, tradePointNames);
        chooseTradePoint.setAdapter(tradePointAdapter);

        //filling measure field
        for (Good good : allGoods) {
            if (good.getName().equals(chooseGood.getSelectedItem().toString())) {
                measureField.setText(good.getMeasure());
            }
        }

        //adding listener for calculating full price
        countField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                fullPriceField.setText(String.valueOf(allGoods.get(chooseGood.getSelectedItemPosition())
                        .getPrice() * Double.parseDouble(countField.getText().toString())));
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Добавить заказ");
        builder.setView(subView);
        builder.create();

        //creating add button functionality
        builder.setPositiveButton("ДОБАВИТЬ", (dialog, which) -> {

            String goodId = null;
            for (Good good : allGoods) {
                if (good.getName().equals(chooseGood.getSelectedItem().toString())) {
                    goodId = String.valueOf(good.getId());
                }
            }

            String tradePointId = null;
            for (TradePoint tradePoint : allTradePoints) {
                if (tradePoint.getName().equals(chooseTradePoint.getSelectedItem().toString())) {
                    tradePointId = String.valueOf(tradePoint.getId());
                }
            }
            final String goodCount = countField.getText().toString();
            final String fPrice = fullPriceField.getText().toString();

            //checking fields for input values
            if(TextUtils.isEmpty(goodId) || TextUtils.isEmpty(tradePointId) || TextUtils.isEmpty(goodCount)){
                Toast.makeText(OrdersActivity.this, "Проверьте введённые данные", Toast.LENGTH_LONG).show();
            } else {
                //creating and adding new order to database
                Order newOrder = new Order(goodId, tradePointId, Double.parseDouble(goodCount), Double.parseDouble(fPrice));
                dbHelper.addOrder(newOrder);

                //refreshing orders screen for update list of orders in recycler view
                finish();
                startActivity(getIntent());
            }
        });

        //creating cancel button functionality
        builder.setNegativeButton("ОТМЕНА", (dialog, which) -> Toast.makeText(OrdersActivity.this, "Добавление отменено", Toast.LENGTH_LONG).show());
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(dbHelper != null){
            dbHelper.close();
        }
    }
}