package com.aliarslanpay.mywardrobe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class AddCombineActivity extends AppCompatActivity {

    EditText combineNameText;
    ImageView imageView1, imageView2, imageView3, imageView4, imageView5;
    Button saveCombineButton, shareCombineButton;
    SQLiteDatabase databaseClothes, databaseCombines;
    String overHeadId, faceId, upperBodyId, lowerBodyId, footId;
    String idd, number;
    Bitmap bitmap1,bitmap2,bitmap3,bitmap4,bitmap5;
    byte[] bytes2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_combine);

        combineNameText = findViewById(R.id.combineNameText);
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);
        saveCombineButton = findViewById(R.id.saveCombineButton);
        shareCombineButton = findViewById(R.id.shareCombineButton);

        databaseCombines = this.openOrCreateDatabase("Combines", MODE_PRIVATE,null);
        databaseClothes = this.openOrCreateDatabase("Clothes", MODE_PRIVATE,null);

        Intent intent = getIntent();
        String info = intent.getStringExtra("info");
        String combineName = intent.getStringExtra("combineName");

        idd = intent.getStringExtra("idd");
        number = intent.getStringExtra("number");
        System.out.println(idd);
        System.out.println(number);
        System.out.println(info);



        if(info != null){
            if (info.matches("new")) {
                combineNameText.setText("");
                saveCombineButton.setVisibility(View.VISIBLE);
                shareCombineButton.setVisibility(View.INVISIBLE);

                Bitmap selectImage = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.selectimage);
                imageView1.setImageBitmap(selectImage);
                imageView2.setImageBitmap(selectImage);
                imageView3.setImageBitmap(selectImage);
                imageView4.setImageBitmap(selectImage);
                imageView5.setImageBitmap(selectImage);


            } else {
                combineNameText.setText(combineName);
                int combineId = intent.getIntExtra("combineId",1);
                saveCombineButton.setVisibility(View.INVISIBLE);
                shareCombineButton.setVisibility(View.VISIBLE);
                System.out.println(combineId);
                try {

                    Cursor cursor = databaseCombines.rawQuery("SELECT * FROM combines WHERE id = ?",new String[] {String.valueOf(combineId)});

                    int overHeadIx = cursor.getColumnIndex("overhead");
                    int facesIx = cursor.getColumnIndex("face");
                    int upperBodyIx = cursor.getColumnIndex("upperbody");
                    int lowerBodyIx = cursor.getColumnIndex("lowerbody");
                    int footIx = cursor.getColumnIndex("foot");
                    int overHeadId=0;
                    int facesId=0;
                    int upperBodyId=0;
                    int lowerBodyId=0;
                    int footId=0;

/*
                    int overHeadId = cursor.getInt(overHeadIx);
                    int facesId = cursor.getInt(facesIx);
                    int upperBodyId = cursor.getInt(upperBodyIx);
                    int lowerBodyId = cursor.getInt(lowerBodyIx);
                    int footId = cursor.getInt(footIx);
*/
                    while (cursor.moveToNext()) {
                        //System.out.println(cursor.getInt(overHeadIx));
                        overHeadId = cursor.getInt(overHeadIx);
                        facesId = cursor.getInt(facesIx);
                        upperBodyId = cursor.getInt(upperBodyIx);
                        lowerBodyId = cursor.getInt(lowerBodyIx);
                        footId = cursor.getInt(footIx);
                        /*
                    byte[] bytes = cursor.getBlob(imageIx);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    imageView.setImageBitmap(bitmap);
*/

                    }
                    cursor = databaseClothes.rawQuery("SELECT * FROM clothes WHERE id = ?",new String[] {String.valueOf(overHeadId)});
                    int a = cursor.getColumnIndex("image");
                    byte[] bytes1=null;
                    while (cursor.moveToNext()){
                        bytes1 = cursor.getBlob(a);
                    }
                    bitmap1 = BitmapFactory.decodeByteArray(bytes1,0,bytes1.length);
                    imageView1.setImageBitmap(bitmap1);

                    cursor = databaseClothes.rawQuery("SELECT * FROM clothes WHERE id = ?",new String[] {String.valueOf(facesId)});
                    int b = cursor.getColumnIndex("image");
                    bytes2=null;
                    while (cursor.moveToNext()){
                        bytes2 = cursor.getBlob(b);
                    }
                    bitmap2 = BitmapFactory.decodeByteArray(bytes2,0,bytes2.length);
                    imageView2.setImageBitmap(bitmap2);


                    cursor = databaseClothes.rawQuery("SELECT * FROM clothes WHERE id = ?",new String[] {String.valueOf(upperBodyId)});
                    int c = cursor.getColumnIndex("image");
                    byte[] bytes3=null;
                    while (cursor.moveToNext()){
                        bytes3 = cursor.getBlob(c);
                    }
                    bitmap3 = BitmapFactory.decodeByteArray(bytes3,0,bytes3.length);
                    imageView3.setImageBitmap(bitmap3);

                    cursor = databaseClothes.rawQuery("SELECT * FROM clothes WHERE id = ?",new String[] {String.valueOf(lowerBodyId)});
                    int d = cursor.getColumnIndex("image");
                    byte[] bytes4=null;
                    while (cursor.moveToNext()){
                        bytes4 = cursor.getBlob(d);
                    }
                    bitmap4 = BitmapFactory.decodeByteArray(bytes4,0,bytes4.length);
                    imageView4.setImageBitmap(bitmap4);

                    cursor = databaseClothes.rawQuery("SELECT * FROM clothes WHERE id = ?",new String[] {String.valueOf(footId)});
                    int e = cursor.getColumnIndex("image");
                    byte[] bytes5=null;
                    while (cursor.moveToNext()){
                        bytes5 = cursor.getBlob(e);
                    }
                    bitmap5 = BitmapFactory.decodeByteArray(bytes5,0,bytes5.length);
                    imageView5.setImageBitmap(bitmap5);

                    cursor.close();
                    //System.out.println("burada: "+overHeadId);
                } catch (Exception e) {

                }


            }
        }



    }

    public void selectImage1(View view) {
        Intent intent = new Intent(AddCombineActivity.this, WardrobeActivity.class);
        intent.putExtra("clothes","choose");
        intent.putExtra("filter","Overhead");
        startActivity(intent);

    }
    public void selectImage2(View view) {
        Intent intent = new Intent(AddCombineActivity.this, WardrobeActivity.class);
        intent.putExtra("clothes","choose");
        intent.putExtra("filter","Face");
        startActivity(intent);

    }
    public void selectImage3(View view) {
        Intent intent = new Intent(AddCombineActivity.this, WardrobeActivity.class);
        intent.putExtra("clothes","choose");
        intent.putExtra("filter","Upper Body");
        startActivity(intent);

    }
    public void selectImage4(View view) {
        Intent intent = new Intent(AddCombineActivity.this, WardrobeActivity.class);
        intent.putExtra("clothes","choose");
        intent.putExtra("filter","Lower Body");
        startActivity(intent);

    }
    public void selectImage5(View view) {
        Intent intent = new Intent(AddCombineActivity.this, WardrobeActivity.class);
        intent.putExtra("clothes","choose");
        intent.putExtra("filter","Foot");
        startActivityForResult(intent,5);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 5 && data != null) {

            String a = data.getStringExtra("idd");



        }


        super.onActivityResult(requestCode, resultCode, data);
    }


    public void saveCombine (View view) {

        try {

            databaseCombines = this.openOrCreateDatabase("Combines", MODE_PRIVATE,null);
            databaseCombines.execSQL("CREATE TABLE IF NOT EXISTS combines (id INTEGER PRIMARY KEY, overhead VARCHAR, face VARCHAR, upperBody VARCHAR, lowerBody VARCHAR, foot VARCHAR)");


            String sqlString = "INSERT INTO combines (overhead, face, upperBody, lowerBody, foot) VALUES (?, ?, ?, ?, ?)";
            SQLiteStatement sqLiteStatement = databaseCombines.compileStatement(sqlString);
            sqLiteStatement.bindString(1, overHeadId);
            sqLiteStatement.bindString(2, faceId);
            sqLiteStatement.bindString(3, upperBodyId);
            sqLiteStatement.bindString(4, lowerBodyId);
            sqLiteStatement.bindString(5, footId);
            sqLiteStatement.execute();

        } catch (Exception e) {

        }


        Intent intent = new Intent(AddCombineActivity.this,CabinRoomActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public byte[] getData(String id) {

        //Integer idd = (Integer)id;
        Integer idd =Integer.parseInt(id);
        byte[] a = null;

        try {
            SQLiteDatabase database = this.openOrCreateDatabase("Clothes",MODE_PRIVATE,null);

            Cursor cursor = database.rawQuery("SELECT * FROM clothes WHERE id=5", null);
            int imageIx = cursor.getColumnIndex("image");

            a = cursor.getBlob(imageIx);
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return a;
    }

    public void shareCombine (View view) {
/*
        Intent hyvesIntent = new Intent(Intent.ACTION_SEND);
        //hyvesIntent.setPackage("com.hyves.android.application");
        hyvesIntent.setType("image/jpg");
        hyvesIntent.putExtra("image", bytes2);
        startActivityForResult(hyvesIntent, 666);
*/
        ArrayList<Uri> uris = new ArrayList<>();
        Uri uri = saveImageShare(bitmap1, 1);
        uris.add(uri);
        Uri uri2 = saveImageShare(bitmap2,2);
        uris.add(uri2);
        Uri uri3 = saveImageShare(bitmap3,3);
        uris.add(uri3);
        Uri uri4 = saveImageShare(bitmap4,4);
        uris.add(uri4);
        Uri uri5 = saveImageShare(bitmap5,5);
        uris.add(uri5);

        Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        intent.putExtra(Intent.EXTRA_STREAM, uris);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/jpg");
        startActivity(intent);
    }

    public Uri saveImageShare(Bitmap image, int a) {
        File imagesFolder = new File(getCacheDir(), "images");
        Uri uri = null;
        try {
            imagesFolder.mkdirs();
            File file = new File(imagesFolder, "shared_"+a+"image.png");

            FileOutputStream stream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 90, stream);
            stream.flush();
            stream.close();
            uri = FileProvider.getUriForFile(this, "com.aliarslanpay.mywardrobe", file);

        } catch (IOException e) {

        }
        return uri;
    }
}