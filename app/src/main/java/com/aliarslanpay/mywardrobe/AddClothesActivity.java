package com.aliarslanpay.mywardrobe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddClothesActivity extends AppCompatActivity {
    Bitmap selectedImage;
    TextView sectionText, typeText;
    ImageView addClothes;
    EditText colourText, dateText, patternText, priceText;
    Button addClothesButton;
    SQLiteDatabase database;

    String drawerName;
    String typeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clothes);

        Intent intent = getIntent();
        drawerName = intent.getStringExtra("drawerName");
        typeName = intent.getStringExtra("typeName");

        sectionText = findViewById(R.id.sectionText);
        sectionText.setText(typeName);
        typeText = findViewById(R.id.typeText);
        typeText.setText(drawerName);
        addClothes = findViewById(R.id.addClothesImage);
        colourText = findViewById(R.id.colourText);
        dateText = findViewById(R.id.dateText);
        patternText = findViewById(R.id.patternText);
        priceText = findViewById(R.id.priceText);
        addClothesButton = findViewById(R.id.addClothesButton);

    }

    public void selectImage(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String [] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        else {
            Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intentToGallery,2);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //ilgili izni verirse
        if(requestCode == 1){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentToGallery,2);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            Uri imageData = data.getData();
            //String a = getRealPathFromURI(imageData);
            //System.out.println(a);
            try {
                if(Build.VERSION.SDK_INT >= 28) {
                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), imageData);
                    selectedImage = ImageDecoder.decodeBitmap(source);
                    addClothes.setImageBitmap(selectedImage);
                }
                else{
                    selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageData);
                    addClothes.setImageBitmap(selectedImage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void save(View view) {
        String colour = colourText.getText().toString();
        String date = dateText.getText().toString();
        String pattern = patternText.getText().toString();
        String price = priceText.getText().toString();
        Bitmap smallImage = makeSmallerImage(selectedImage,200);

        //görseli aldık ve byte dizisine çevirdik
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        smallImage.compress(Bitmap.CompressFormat.PNG, 75, outputStream);
        byte[] byteArray = outputStream.toByteArray();




        try {
            database = this.openOrCreateDatabase("Clothes",MODE_PRIVATE,null);
            database.execSQL("CREATE TABLE IF NOT EXISTS clothes (id INTEGER PRIMARY KEY, typeName VARCHAR," +
                    " drawerName VARCHAR, colour VARCHAR, pattern VARCHAR, date VARCHAR, price VARCHAR, image BLOB)");


            String sqlString = "INSERT INTO clothes (typeName, drawerName, colour, pattern, date, price, image) VALUES (?, ?, ?, ?, ?, ?, ?)";
            SQLiteStatement sqLiteStatement = database.compileStatement(sqlString);
            sqLiteStatement.bindString(1,typeName);
            sqLiteStatement.bindString(2,drawerName);
            sqLiteStatement.bindString(3,colour);
            sqLiteStatement.bindString(4,pattern);
            sqLiteStatement.bindString(5,date);
            sqLiteStatement.bindString(6,price);
            sqLiteStatement.bindBlob(7,byteArray);
            sqLiteStatement.execute();

        } catch (Exception e) {

        }
        Intent intent = new Intent(AddClothesActivity.this,WardrobeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    public Bitmap makeSmallerImage(Bitmap image, int maximumSize) {
        int width = image.getWidth();
        int height = image.getHeight();
        float bitmapRatio = (float) width / (float) height;

        //yatay mı dikey mi kontrolü, ona göre belirli oranda küçültme işlemi
        if (bitmapRatio > 1) {
            width = maximumSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maximumSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image,width,height,true);
    }

}