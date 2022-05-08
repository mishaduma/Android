package com.example.firstproject.goods;

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
import com.example.firstproject.entities.Good;

import java.util.ArrayList;

public class GoodsActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private ArrayList<Good> allGoods = new ArrayList<>();
    private GoodsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);

        //creating recycler view with goods cards
        RecyclerView goodView = findViewById(R.id.goods_list);
        goodView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DBHelper(this);
        allGoods = dbHelper.listGoods();

        if (allGoods.size() > 0) {
            goodView.setVisibility(View.VISIBLE);
            adapter = new GoodsAdapter(this, dbHelper);
            goodView.setAdapter(adapter);
        } else {
            goodView.setVisibility(View.GONE);
            Toast.makeText(this, "Товаров нет. Нужно добавить", Toast.LENGTH_LONG).show();
        }

        //setting onclick listener for button to launch alert dialog
        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(view -> addTaskDialog());
    }

    private void addTaskDialog(){
        View subView = LayoutInflater.from(this).inflate(R.layout.add_good_layout, null);

        final EditText nameField = subView.findViewById(R.id.enter_good_name);
        final EditText measureField = subView.findViewById(R.id.enter_good_measure);
        final EditText priceField = subView.findViewById(R.id.enter_good_price);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Добавить товар");
        builder.setView(subView);
        builder.create();

        //creating add button functionality
        builder.setPositiveButton("ДОБАВИТЬ", (dialog, which) -> {

            final String name = nameField.getText().toString();
            final String measure = measureField.getText().toString();
            final double price = Double.parseDouble(priceField.getText().toString());

            //checking fields for input values
            if(TextUtils.isEmpty(name) || TextUtils.isEmpty(measure) || TextUtils.isEmpty(String.valueOf(price))){
                Toast.makeText(GoodsActivity.this, "Проверьте введённые данные", Toast.LENGTH_LONG).show();
            } else {
                //creating and adding new good to database
                Good newGood = new Good(name, measure, price);
                dbHelper.addGood(newGood);

                //refreshing goods screen for update list of goods in recycler view
                finish();
                startActivity(getIntent());
            }
        });

        //creating cancel button functionality
        builder.setNegativeButton("ОТМЕНА", (dialog, which) -> Toast.makeText(GoodsActivity.this, "Добавление отменено", Toast.LENGTH_LONG).show());
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}