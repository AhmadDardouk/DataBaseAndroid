package com.example.database;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

public class Display2 extends AppCompatActivity {
TextView Display;
MultiAutoCompleteTextView disp;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display2);
//        Display = findViewById(R.id.display2);
        disp=findViewById(R.id.dis);

//        Display.setText("");
        Intent intent = new Intent(this, MyService.class);
        intent.setAction("Display2");
        startForegroundService(intent);
        BroadcastReceiver broadcast = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("toDisplay")) {

                    String result = intent.getStringExtra("result");
                    disp.setText( disp.getText().toString()+result);
                }
            }
        };
        IntentFilter Filter1 = new IntentFilter();
        Filter1.addAction("toDisplay");
        registerReceiver(broadcast, Filter1);

    }

    @Override
    public void onBackPressed() {

        disp.setText("");

        super.onBackPressed();
    }
}