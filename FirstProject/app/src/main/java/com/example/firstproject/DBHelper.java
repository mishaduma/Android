package com.example.firstproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.firstproject.entities.Good;
import com.example.firstproject.entities.Order;
import com.example.firstproject.entities.TradePoint;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "app.db";
    static final String GOODS = "goods";
    static final String TRADE_POINTS = "trade_points";
    static final String ORDERS = "orders";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + GOODS + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, measure TEXT, price REAL)");
        db.execSQL("CREATE TABLE " + TRADE_POINTS + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, address TEXT)");
        db.execSQL("CREATE TABLE " + ORDERS + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, good_id TEXT, " +
                "trade_point_id TEXT, count REAL, full_price REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + GOODS);
        db.execSQL("DROP TABLE IF EXISTS " + TRADE_POINTS);
        db.execSQL("DROP TABLE IF EXISTS " + ORDERS);
        onCreate(db);
    }

    public ArrayList<Good> listGoods() {
        String sql = "SELECT * FROM " + GOODS;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Good> storeGoods = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String measure = cursor.getString(2);
                Double price = Double.parseDouble(cursor.getString(3));
                storeGoods.add(new Good(id, name, measure, price));
            }
        }
        cursor.close();
        return storeGoods;
    }

    public void addGood(Good good) {
        ContentValues values = new ContentValues();
        values.put("name", good.getName());
        values.put("measure", good.getMeasure());
        values.put("price", good.getPrice());
        this.getWritableDatabase().insert(GOODS, null, values);
    }

    public void updateGood(Good good) {
        ContentValues values = new ContentValues();
        values.put("name", good.getName());
        values.put("measure", good.getMeasure());
        values.put("price", good.getPrice());
        this.getWritableDatabase().update(GOODS, values, "_id = ?",
                new String[] {String.valueOf(good.getId())});
    }

    public void deleteGood(int id) {
        this.getWritableDatabase().delete(GOODS, "_id = ?",
                new String[] {String.valueOf(id)});
    }

    public ArrayList<TradePoint> listTradePoints() {
        String sql = "SELECT * FROM " + TRADE_POINTS;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<TradePoint> storeTradePoints = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String address = cursor.getString(2);
                storeTradePoints.add(new TradePoint(id, name, address));
            }
        }
        cursor.close();
        return storeTradePoints;
    }

    public void addTradePoint(TradePoint tradePoint) {
        ContentValues values = new ContentValues();
        values.put("name", tradePoint.getName());
        values.put("address", tradePoint.getAddress());
        this.getWritableDatabase().insert(TRADE_POINTS, null, values);
    }

    public void updateTradePoint(TradePoint tradePoint) {
        ContentValues values = new ContentValues();
        values.put("name", tradePoint.getName());
        values.put("address", tradePoint.getAddress());
        this.getWritableDatabase().update(TRADE_POINTS, values, "_id = ?",
                new String[] {String.valueOf(tradePoint.getId())});
    }

    public void deleteTradePoint(int id) {
        this.getWritableDatabase().delete(TRADE_POINTS, "_id = ?",
                new String[] {String.valueOf(id)});
    }


    public ArrayList<Order> listOrders() {
        String sql = "SELECT * FROM " + ORDERS;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Order> storeOrders = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                int id = Integer.parseInt(cursor.getString(0));
                String goodId = cursor.getString(1);
                String tradePointId = cursor.getString(2);
                Double count = Double.parseDouble(cursor.getString(3));
                Double fullPrice = Double.parseDouble(cursor.getString(4));
                storeOrders.add(new Order(id, goodId, tradePointId, count, fullPrice));
            }
        }
        cursor.close();
        return storeOrders;
    }

    public void addOrder(Order order) {
        ContentValues values = new ContentValues();
        values.put("good_id", order.getGoodId());
        values.put("trade_point_id", order.getTradePointId());
        values.put("count", order.getCount());
        values.put("full_price", order.getFullPrice());
        this.getWritableDatabase().insert(ORDERS, null, values);
    }

    public void deleteOrder(int id) {
        this.getWritableDatabase().delete(ORDERS, "_id = ?",
                new String[] {String.valueOf(id)});
    }
}
