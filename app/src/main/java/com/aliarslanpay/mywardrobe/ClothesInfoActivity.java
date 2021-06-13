package com.aliarslanpay.mywardrobe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ClothesInfoActivity extends AppCompatActivity {

    ImageView clothesImage;
    TextView priceText, dateText, patternText, colourText, drawerText, sectionText;
    Button chooseClothes;
    String id, ttype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes_info);


        clothesImage = findViewById(R.id.clothesInfoImageView);
        priceText = findViewById(R.id.priceTextView);
        dateText = findViewById(R.id.dateTextView);
        patternText = findViewById(R.id.patternTextView);
        colourText = findViewById(R.id.colourTextView);
        drawerText = findViewById(R.id.drawerTextView);
        sectionText = findViewById(R.id.sectionTextView);
        chooseClothes = findViewById(R.id.chooseButton);

        Intent intent = getIntent();
        String info = intent.getStringExtra("clothes");
        byte[] bytes = intent.getByteArrayExtra("image");


        ttype = intent.getStringExtra("ttype");
        sectionText.setText("Section: "+ttype);
        String ddrawer = intent.getStringExtra("ddrawer");
        drawerText.setText("Clothing Type: "+ddrawer);
        String ccolour = intent.getStringExtra("ccolour");
        colourText.setText("Colour: "+ccolour);
        String ppattern = intent.getStringExtra("ppattern");
        patternText.setText("Pattern: "+ppattern);
        String ddate = intent.getStringExtra("ddate");
        dateText.setText("Purchase date: "+ddate);
        String pprice = intent.getStringExtra("pprice");
        priceText.setText("Price: "+pprice+" TL");


        id = intent.getStringExtra("id");
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        clothesImage.setImageBitmap(bitmap);


        if(info.matches("view")) {
            chooseClothes.setVisibility(View.INVISIBLE);
        }
        else{
            chooseClothes.setVisibility(View.VISIBLE);
        }
    }

    public void chooseForCombine (View view) {

        Intent output = new Intent(ClothesInfoActivity.this, AddCombineActivity.class);
        output.putExtra("idd",id);
        if(ttype.matches("Foot")){
            output.putExtra("number","3");
        }

        //output.putExtra("typee",)
        //output.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(output);
    }

}