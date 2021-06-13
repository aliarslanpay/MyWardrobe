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

public class EventsActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> nameArray;
    ArrayList<Integer> idArray;
    ArrayAdapter arrayAdapter;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        database = this.openOrCreateDatabase("Events",MODE_PRIVATE,null);
        listView = findViewById(R.id.eventListView);
        nameArray = new ArrayList<String>();
        idArray = new ArrayList<Integer>();


        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, nameArray);
        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(EventsActivity.this,EventDetailsActivity.class);
                intent.putExtra("eventId",idArray.get(position));
                intent.putExtra("info_event","old");
                startActivity(intent);


            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                new AlertDialog.Builder(EventsActivity.this)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this event ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try{
                                    database.execSQL("DELETE FROM events WHERE id= ?",new String[] {String.valueOf(position+1)});
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }
                                nameArray.remove(position);
                                arrayAdapter.notifyDataSetChanged();

                                Toast.makeText(EventsActivity.this,"The event has been deleted!",Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("No",null).show();
                return true;
            }
        });

        getData();

    }

    public void getData() {

        try {
            SQLiteDatabase database = this.openOrCreateDatabase("Events",MODE_PRIVATE,null);

            Cursor cursor = database.rawQuery("SELECT * FROM events", null);
            int nameIx = cursor.getColumnIndex("name");
            int idIx = cursor.getColumnIndex("id");

            while (cursor.moveToNext()) {
                nameArray.add(cursor.getString(nameIx));
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

        //Inflater
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_event,menu);


        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.add_event_item) {
            Intent intent = new Intent(EventsActivity.this, EventDetailsActivity.class);
            intent.putExtra("info_event","new");
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}