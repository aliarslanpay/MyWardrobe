package com.aliarslanpay.mywardrobe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class WardrobeActivity extends AppCompatActivity {
    ArrayList<String> drawerNameList;
    ArrayList<String> typeNameList;
    ArrayList<Integer> idArray;
    ArrayList<byte[]> typeImageList;
    ListView listView;
    ArrayAdapter arrayAdapter;
    SQLiteDatabase database;
    SQLiteDatabase database2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wardrobe);
        database = this.openOrCreateDatabase("Drawers",MODE_PRIVATE,null);
        database2 = this.openOrCreateDatabase("Clothes",MODE_PRIVATE,null);

        drawerNameList = new ArrayList<>();
        typeNameList = new ArrayList<>();
        idArray = new ArrayList<>();
        typeImageList = new ArrayList<>();
        listView = findViewById(R.id.listView);

        Intent intent = getIntent();
        String clothes = intent.getStringExtra("clothes");
        String filter = intent.getStringExtra("filter");
        System.out.println(clothes);



        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, drawerNameList);
        listView.setAdapter(arrayAdapter);
/*if(clothes.matches("choose")){
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(WardrobeActivity.this, ClothesActivity.class);
            intent.putExtra("drawerName",drawerNameList.get(position));
            intent.putExtra("typeName",typeNameList.get(position));
            intent.putExtra("clothes",clothes);
            startActivityForResult(intent, 5);
        }
    });
}*/
//else{
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(WardrobeActivity.this, ClothesActivity.class);
            intent.putExtra("drawerName",drawerNameList.get(position));
            intent.putExtra("typeName",typeNameList.get(position));
            intent.putExtra("clothes",clothes);
            startActivity(intent);
        }
    });
    listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            new AlertDialog.Builder(WardrobeActivity.this)
                    .setTitle("Are you sure?")
                    .setMessage("Do you want to delete this drawer with clothes?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //Cursor cursor = database.rawQuery("SELECT * FROM arts WHERE drawerName = ?",new String[] {drawerNameList.get(position)});

                            //int drawerNameIx = cursor.getColumnIndex("drawerName");
                            //System.out.println("pos: "+position+" which: "+which);
                            try{
                                database.execSQL("DELETE FROM drawers WHERE id= ?",new String[] {String.valueOf(position+1)});
                                database2.execSQL("DELETE FROM clothes WHERE drawerName= ?",new String[] {drawerNameList.get(position)});


                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                            drawerNameList.remove(position);
                            arrayAdapter.notifyDataSetChanged();

                            Toast.makeText(WardrobeActivity.this,"The drawer has been deleted!",Toast.LENGTH_LONG).show();
                        }
                    })
                    .setNegativeButton("No",null).show();
            return true;
        }
    });
//}

        getData(clothes, filter);
    }

    public void getData(String where, String filter) {
        try {
            //SQLiteDatabase database = this.openOrCreateDatabase("Drawers",MODE_PRIVATE,null);

            Cursor cursor = database.rawQuery("SELECT * FROM drawers", null);

            if(where != null){
                if(where.matches("choose")){
                    cursor = database.rawQuery("SELECT * FROM drawers WHERE typeName = ?", new String[]{filter});
                }
            }
            else{
                cursor = database.rawQuery("SELECT * FROM drawers", null);
            }


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

            arrayAdapter.notifyDataSetChanged();

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
            Intent intent = new Intent(WardrobeActivity.this, AddDrawerActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}