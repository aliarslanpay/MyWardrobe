package com.aliarslanpay.mywardrobe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class ClothesActivity extends AppCompatActivity {
    String drawerName;
    String typeName;
    GridView gridView;
    ClothesCustomAdapter clothesCustomAdapter;
    ArrayList<byte[]> typeImageList;
    ArrayList<String> clothesNumber;
    ArrayList<String> clothesType, clothesDrawer, clothesColour, clothesPattern, clothesDate, clothesPrice;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes);

        database = this.openOrCreateDatabase("Clothes",MODE_PRIVATE,null);

        typeImageList = new ArrayList<>();
        clothesNumber = new ArrayList<>();

        clothesType = new ArrayList<>();
        clothesDrawer = new ArrayList<>();
        clothesColour = new ArrayList<>();
        clothesPattern = new ArrayList<>();
        clothesDate = new ArrayList<>();
        clothesPrice = new ArrayList<>();

        gridView = findViewById(R.id.gridView);

        Intent intent = getIntent();
        drawerName = intent.getStringExtra("drawerName");
        typeName = intent.getStringExtra("typeName");


        String clothes = intent.getStringExtra("clothes");

        String filter = intent.getStringExtra("filter");

        clothesCustomAdapter = new ClothesCustomAdapter(typeImageList,this);

        gridView.setAdapter(clothesCustomAdapter);
        /*if(clothes.matches("choose")){
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ClothesActivity.this, ClothesInfoActivity.class);
                    intent.putExtra("image",typeImageList.get(position));
                    intent.putExtra("id",clothesNumber.get(position));

                    intent.putExtra("ttype",clothesType.get(position));
                    intent.putExtra("ddrawer",clothesDrawer.get(position));
                    intent.putExtra("ccolour",clothesColour.get(position));
                    intent.putExtra("ppattern",clothesPattern.get(position));
                    intent.putExtra("ddate",clothesDate.get(position));
                    intent.putExtra("pprice",clothesPrice.get(position));

                    intent.putExtra("clothes",clothes);
                    intent.putExtra("filter",filter);
                    startActivityForResult(intent, 5);
                }
            });
        }
        else{*/
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ClothesActivity.this, ClothesInfoActivity.class);
                    intent.putExtra("image",typeImageList.get(position));
                    intent.putExtra("id",clothesNumber.get(position));

                    intent.putExtra("ttype",clothesType.get(position));
                    intent.putExtra("ddrawer",clothesDrawer.get(position));
                    intent.putExtra("ccolour",clothesColour.get(position));
                    intent.putExtra("ppattern",clothesPattern.get(position));
                    intent.putExtra("ddate",clothesDate.get(position));
                    intent.putExtra("pprice",clothesPrice.get(position));

                    intent.putExtra("clothes",clothes);
                    intent.putExtra("filter",filter);
                    startActivity(intent);
                }
            });
            gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    new AlertDialog.Builder(ClothesActivity.this)
                            .setTitle("Are you sure?")
                            .setMessage("Do you want to delete this clothes?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try{
                                        database.execSQL("DELETE FROM clothes WHERE id= ?",new String[] {String.valueOf(position+1)});


                                    }
                                    catch (Exception e){
                                        e.printStackTrace();
                                    }
                                    typeImageList.remove(position);
                                    clothesCustomAdapter.notifyDataSetChanged();

                                    Toast.makeText(ClothesActivity.this,"The clothes has been deleted!",Toast.LENGTH_LONG).show();
                                }
                            })
                            .setNegativeButton("No",null).show();

                    return true;
                }
            });
        //}


        getData();
    }

    public void getData() {
        try {
            //SQLiteDatabase database = this.openOrCreateDatabase("Clothes",MODE_PRIVATE,null);

            Cursor cursor = database.rawQuery("SELECT * FROM clothes WHERE drawerName = ?", new String[]{drawerName});
            int imageIx = cursor.getColumnIndex("image");
            int idIX = cursor.getColumnIndex("id");
            int typeNameIX = cursor.getColumnIndex("typeName");
            int drawerNameIX = cursor.getColumnIndex("drawerName");
            int colourIX = cursor.getColumnIndex("colour");
            int patternIX = cursor.getColumnIndex("pattern");
            int dateIX = cursor.getColumnIndex("date");
            int priceIX = cursor.getColumnIndex("price");

            while (cursor.moveToNext()) {
                typeImageList.add(cursor.getBlob(imageIx));

                Integer a =cursor.getInt(idIX);
                clothesNumber.add(a.toString());

                clothesType.add(cursor.getString(typeNameIX));
                clothesDrawer.add(cursor.getString(drawerNameIX));
                clothesColour.add(cursor.getString(colourIX));
                clothesPattern.add(cursor.getString(patternIX));
                clothesDate.add(cursor.getString(dateIX));
                clothesPrice.add(cursor.getString(priceIX));
            }

            clothesCustomAdapter.notifyDataSetChanged();

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_clothes,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_clothes_item) {
            Intent intent = new Intent(ClothesActivity.this, AddClothesActivity.class);
            intent.putExtra("drawerName",drawerName);
            intent.putExtra("typeName",typeName);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}