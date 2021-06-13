package com.aliarslanpay.mywardrobe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private BatteryReceiver mBatteryReceiver = new BatteryReceiver();
    private IntentFilter mIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        SQLiteDatabase database = this.openOrCreateDatabase("Clothes",MODE_PRIVATE,null);
        Cursor cursor = database.rawQuery("SELECT * FROM clothes", null);

        int a = cursor.getColumnIndex("id");
        int b = cursor.getColumnIndex("drawerName");
        int c = cursor.getColumnIndex("colour");
        while(cursor.moveToNext()){
            System.out.println("Id: "+cursor.getInt(a));
            System.out.println("Name: "+cursor.getString(b));
            System.out.println("Age: " + cursor.getString(c));
        }
        cursor.close();
*/

        SQLiteDatabase database = this.openOrCreateDatabase("Combines",MODE_PRIVATE,null);
        database.execSQL("CREATE TABLE IF NOT EXISTS combines (id INTEGER PRIMARY KEY, name VARCHAR, overhead INTEGER, face INTEGER, upperbody INTEGER, lowerbody INTEGER, foot INTEGER)");

        //database.execSQL("INSERT INTO combines (name, overhead, face, upperbody, lowerbody, foot) VALUES ('Relax',4,1,9,10,3)");
        database.execSQL("INSERT INTO combines (name, overhead, face, upperbody, lowerbody, foot) VALUES ('Relax',8,3,7,4,5)");
        database.execSQL("INSERT INTO combines (name, overhead, face, upperbody, lowerbody, foot) VALUES ('Casual',8,3,7,4,5)");
        /*SQLiteDatabase database = this.openOrCreateDatabase("Events",MODE_PRIVATE,null);
        database.execSQL("CREATE TABLE IF NOT EXISTS events (id INTEGER PRIMARY KEY, name VARCHAR, type VARCHAR, date VARCHAR, combine VARCHAR, address VARCHAR)");

        //database.execSQL("INSERT INTO combines (name, overhead, face, upperbody, lowerbody, foot) VALUES ('Relax',4,1,9,10,3)");
        database.execSQL("INSERT INTO events (name, type, date, combine ,address) VALUES ('Museum Visit','Informal','15.09.2020','Casual','Fatih/Istanbul')");
        database.execSQL("INSERT INTO events (name, type, date, combine ,address) VALUES ('Birthday','Informal','06.03.2020','Casual','Güngören/Istanbul')");*/

    }
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mBatteryReceiver,mIntentFilter);
    }
    @Override
    protected void onPause() {
        unregisterReceiver(mBatteryReceiver);
        super.onPause();
    }
    public void myWardrobe(View view) {

        Intent intent = new Intent(MainActivity.this, WardrobeActivity.class);
        intent.putExtra("clothes","view");
        startActivity(intent);

    }

    public void myCabinet(View view) {

        Intent intent = new Intent(MainActivity.this, CabinRoomActivity.class);
        startActivity(intent);

    }

    public void myEvents(View view) {

        Intent intent = new Intent(MainActivity.this, EventsActivity.class);
        startActivity(intent);

    }
}