package com.example.firstproject.tradepoints;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firstproject.DBHelper;
import com.example.firstproject.R;
import com.example.firstproject.entities.TradePoint;

import java.util.ArrayList;

public class TradePointsActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private ArrayList<TradePoint> allTradePoints = new ArrayList<>();
    private TradePointsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_points);

        //creating recycler view with trade points cards
        RecyclerView tradePointView = findViewById(R.id.trade_points_list);
        tradePointView.setLayoutManager(new LinearLayoutManager(this));
        dbHelper = new DBHelper(this);
        allTradePoints = dbHelper.listTradePoints();

        if (allTradePoints.size() > 0) {
            tradePointView.setVisibility(View.VISIBLE);
            adapter = new TradePointsAdapter(this, dbHelper);
            tradePointView.setAdapter(adapter);
        } else {
            tradePointView.setVisibility(View.GONE);
            Toast.makeText(this, "Торговых точек нет. Нужно добавить", Toast.LENGTH_LONG).show();
        }

        //setting onclick listener for button to launch alert dialog
        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(view -> addTaskDialog());
    }

    private void addTaskDialog(){

        View subView = LayoutInflater.from(this).inflate(R.layout.add_trade_point_layout, null);

        final EditText nameField = subView.findViewById(R.id.enter_trade_point_name);
        final EditText addressField = subView.findViewById(R.id.enter_trade_point_address);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Добавить торговую точку");
        builder.setView(subView);
        builder.create();

        //creating add button functionality
        builder.setPositiveButton("ДОБАВИТЬ", (dialog, which) -> {

            final String name = nameField.getText().toString();
            final String address = addressField.getText().toString();

            //checking fields for input values
            if(TextUtils.isEmpty(name) || TextUtils.isEmpty(address)){
                Toast.makeText(TradePointsActivity.this, "Проверьте введённые данные", Toast.LENGTH_LONG).show();
            } else {
                //creating and adding new trade point to database
                TradePoint tradePoint = new TradePoint(name, address);
                dbHelper.addTradePoint(tradePoint);

                //refreshing trade points screen for update list of trade points recycler view
                finish();
                startActivity(getIntent());
            }
        });

        //creating cancel button functionality
        builder.setNegativeButton("ОТМЕНА", (dialog, which) -> Toast.makeText(TradePointsActivity.this, "Добавление отменено", Toast.LENGTH_LONG).show());
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