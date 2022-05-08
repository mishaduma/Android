package com.example.firstproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.firstproject.goods.GoodsActivity;
import com.example.firstproject.orders.OrdersActivity;
import com.example.firstproject.tradepoints.TradePointsActivity;

public class MainActivity extends AppCompatActivity {

    //creating main screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //method for start goods screen
    public void startAllGoodsActivity(View v) {
        Intent intent = new Intent(this, GoodsActivity.class);
        startActivity(intent);
    }

    //method for start trade points screen
    public void startAllTradePointsActivity(View v) {
        Intent intent = new Intent(this, TradePointsActivity.class);
        startActivity(intent);
    }

    //method for start orders screen
    public void startAllOrdersActivity(View v) {
        Intent intent = new Intent(this, OrdersActivity.class);
        startActivity(intent);
    }
}