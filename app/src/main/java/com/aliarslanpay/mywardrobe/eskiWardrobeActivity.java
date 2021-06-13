package com.aliarslanpay.mywardrobe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

public class eskiWardrobeActivity extends AppCompatActivity {
    ArrayList<String> drawerNameList;
    ArrayList<String> typeNameList;
    ArrayList<Integer> idArray;
    ArrayList<byte[]> typeImageList;
    //ArrayList<Uri> typeImageList;

    eskiWardrobeRecyclerAdapter wardrobeRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eski_wardrobe);

        drawerNameList = new ArrayList<>();
        typeNameList = new ArrayList<>();
        idArray = new ArrayList<>();
        typeImageList = new ArrayList<>();



        //RecyclerView

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        wardrobeRecyclerAdapter = new eskiWardrobeRecyclerAdapter(typeNameList,drawerNameList, typeImageList);

        recyclerView.setAdapter(wardrobeRecyclerAdapter);

        getData();



    }

    public void getData() {
        try {
            SQLiteDatabase database = this.openOrCreateDatabase("Drawers",MODE_PRIVATE,null);

            Cursor cursor = database.rawQuery("SELECT * FROM drawers", null);
            int typeIx = cursor.getColumnIndex("typeName");
            int nameIx = cursor.getColumnIndex("drawerName");
            int idIx = cursor.getColumnIndex("id");
            int imageIx = cursor.getColumnIndex("image");

            while (cursor.moveToNext()) {
                typeImageList.add(cursor.getBlob(imageIx));
                drawerNameList.add(cursor.getString(nameIx));
                typeNameList.add(cursor.getString(typeIx));
                idArray.add(cursor.getInt(idIx));

            }

            wardrobeRecyclerAdapter.notifyDataSetChanged();

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_drawer,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_drawer_item) {
            Intent intent = new Intent(eskiWardrobeActivity.this, AddDrawerActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}