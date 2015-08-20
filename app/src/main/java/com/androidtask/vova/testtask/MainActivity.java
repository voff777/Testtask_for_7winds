package com.androidtask.vova.testtask;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView lvData;
    DB db;
    SimpleCursorAdapter scAdapter;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvData = (ListView) findViewById(R.id.listView1);

        db = new DB(this);
        db.open();

        cursor = db.getAllData();
        startManagingCursor(cursor);

        String[] from = new String[] { db.COLUMN_IMG, db.COLUMN_NAME, db.COLUMN_ABOUT, db.COLUMN_PRICE };
        int[] to = new int[] { R.id.imagePhoto, R.id.textName, R.id.textAbout, R.id.textPrice};

        scAdapter = new SimpleCursorAdapter(this, R.layout.item, cursor, from, to);
        lvData = (ListView) findViewById(R.id.listView1);
        lvData.setAdapter(scAdapter);

        registerForContextMenu(lvData);

        lvData.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intentcall = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+Uri.encode("*100#")));
            startActivity(intentcall);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

        cursor = (Cursor) arg0.getItemAtPosition(arg2);
        //int id = Integer.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(db.COLUMN_ID)));

        String price = cursor.getString(cursor.getColumnIndexOrThrow(db.COLUMN_PRICE));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(db.COLUMN_NAME));

        //Toast.makeText(getApplicationContext(), n+"  "+s, Toast.LENGTH_SHORT).show();

        Intent intent_for_calc = new Intent(this, CalcActivity.class);
        intent_for_calc.putExtra("name", name);
        intent_for_calc.putExtra("price", price);
        startActivity(intent_for_calc);
    }

}
