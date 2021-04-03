package com.example.database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView nav = findViewById(R.id.nav);
        nav.setOnNavigationItemSelectedListener(this);
        nav.setClickable ( true );
        nav.setSelected ( true );
//        SQLiteDatabase myData=getApplicationContext().openOrCreateDatabase("Employee",MODE_PRIVATE,null);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.Insert)
        {
            Intent i = new Intent(this,Insert.class);

            startActivity(i);
        }
        else if(item.getItemId()==R.id.Delete)
        {
            Intent i = new Intent(this,Delete.class);

            startActivity(i);
        }
        else if(item.getItemId()==R.id.Modify)
        {
            Intent i = new Intent(this,Modify.class);

            startActivity(i);
        }
        else if(item.getItemId()==R.id.Display)
        {
            Intent i = new Intent(this,Display2.class);

            startActivity(i);
        }
        else if(item.getItemId()==R.id.Search)
        {
            Intent i = new Intent(this,Search.class);

            startActivity(i);
        }

        return  false;
    }
}