package com.aliarslanpay.mywardrobe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EventDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner spinner;
    EditText eventName, eventType, eventDate, eventAddress;
    TextView combineName;
    String combine;
    ArrayList<String> combineNames;
    SQLiteDatabase database;
    Button button4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        eventName = findViewById(R.id.editTextEventName);
        eventDate = findViewById(R.id.editTextEventDate);
        eventType = findViewById(R.id.editTextEventTypeName);
        eventAddress = findViewById(R.id.editTextEventAddress);
        combineName = findViewById(R.id.textView3);
        button4 = findViewById(R.id.button4);
        Intent intent = getIntent();
        String info = intent.getStringExtra("info_event");
        combineNames = new ArrayList<>();
        try {
            database = this.openOrCreateDatabase("Combines",MODE_PRIVATE,null);
            Cursor cursor = database.rawQuery("SELECT * FROM combines", null);
            int nameIx = cursor.getColumnIndex("name");
            while (cursor.moveToNext()) {
                combineNames.add(cursor.getString(nameIx));
            }
            cursor.close();
            System.out.println(combineNames.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }

        spinner = findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, combineNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        if (info.matches("old")){
            int eventId = intent.getIntExtra("eventId",1);
            database = this.openOrCreateDatabase("Events",MODE_PRIVATE,null);
            button4.setVisibility(View.INVISIBLE);
            spinner.setVisibility(View.INVISIBLE);

            try {

                Cursor cursor = database.rawQuery("SELECT * FROM events WHERE id = ?",new String[] {String.valueOf(eventId)});
                int nameIx = cursor.getColumnIndex("name");
                int typeIx = cursor.getColumnIndex("type");
                int dateIx = cursor.getColumnIndex("date");
                int combineIx = cursor.getColumnIndex("combine");
                int addressIx = cursor.getColumnIndex("address");

                while (cursor.moveToNext()) {

                    eventName.setText(cursor.getString(nameIx));
                    eventType.setText(cursor.getString(typeIx));
                    eventDate.setText(cursor.getString(dateIx));
                    combineName.setText(cursor.getString(combineIx));
                    eventAddress.setText(cursor.getString(addressIx));


                }

                cursor.close();

            } catch (Exception e) {

            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        combine = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void saveEvent (View view) {
        String name = eventName.getText().toString();
        String type = eventType.getText().toString();
        String date = eventDate.getText().toString();
        String address = eventAddress.getText().toString();
        try {

            database = this.openOrCreateDatabase("Events",MODE_PRIVATE,null);
            database.execSQL("CREATE TABLE IF NOT EXISTS events (id INTEGER PRIMARY KEY, name VARCHAR, type VARCHAR, date VARCHAR, combine VARCHAR, address VARCHAR)");


            String sqlString = "INSERT INTO combines (name, type, date, combine, address) VALUES (?, ?, ?, ?, ?)";
            SQLiteStatement sqLiteStatement = database.compileStatement(sqlString);
            sqLiteStatement.bindString(1,name);
            sqLiteStatement.bindString(2,type);
            sqLiteStatement.bindString(3,date);
            sqLiteStatement.bindString(4,combine);
            sqLiteStatement.bindString(5,address);
            sqLiteStatement.execute();

        } catch (Exception e) {

        }
        Toast.makeText(EventDetailsActivity.this,"Event named "+name+" has been created!",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(EventDetailsActivity.this,EventsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}