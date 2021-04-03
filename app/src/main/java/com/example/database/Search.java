package com.example.database;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.number.CompactNotation;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Search extends AppCompatActivity implements View.OnClickListener {
    EditText search,results;
    Button search_Button,Back;
    TextView text;
    String names[],sales[],salaries[],rates[],gender[],IDS[];
    String a,b,c,d,e,f;
    private void addNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_home_black_24dp)
                .setContentTitle("Notifications Example")
                .setContentText("This is a test notification");

        Intent notificationIntent = new Intent(this, Search.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SQLiteDatabase myData=getApplicationContext().openOrCreateDatabase("Employee",MODE_PRIVATE,null);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        search=findViewById(R.id.nameSearch);
        results=findViewById(R.id.PrinOn);
        Back=findViewById(R.id.S_Back);
        Back.setOnClickListener(this);
        search_Button=findViewById(R.id.S_Button);
        search_Button.setOnClickListener(this);
        Cursor c=myData.query("t1",null,null,null,null,null,null);
        c.moveToFirst();
        names=new String[c.getCount()];
        sales=new String[c.getCount()];
        salaries=new String[c.getCount()];
        rates=new String[c.getCount()];
        gender=new String[c.getCount()];
        IDS=new String[c.getCount()];
        for(int i=0;i<c.getCount();i++)
        {
            names[i]=c.getString(0);
            salaries[i]=c.getString(1);
            sales[i]=c.getString(2);
            rates[i]=c.getString(3);
            gender[i]=c.getString(4);
            IDS[i]=c.getString(5);
            c.moveToNext();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        if(v==search_Button)
        {
            if (search.getText().toString().length() == 0) {
                search.setHint("required");
            }
            if (search.getText().toString().length() != 0) {
                Intent intent=new Intent(this,MyService.class);
                intent.setAction("search");
                intent.putExtra("id",search.getText().toString());
                startForegroundService(intent);
                BroadcastReceiver broadcast=new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                     if(intent.getAction().equals("toSearch"))
                     {
                         String result=intent.getStringExtra("id");
                         results.setText(result);
                     }
                    }

        };
                IntentFilter Filter1=new IntentFilter();
                Filter1.addAction("toSearch");
                registerReceiver(broadcast,Filter1);
            }}
        if(v==Back)
        {
            onBackPressed();
        }
}}