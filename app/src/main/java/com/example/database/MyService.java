package com.example.database;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class MyService extends Service {
    String id,sale,sex,salary,rate,name;
    String Result = "";
    String names[];
    private  static final String ChannelID=" Dardouk";

    SQLiteDatabase myData;
    public MyService() {
    }
    private void createChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel x;
            x=new NotificationChannel (ChannelID,"My  Hi Channel with you"  , NotificationManager.IMPORTANCE_HIGH);
            NotificationManager  man= (NotificationManager)getSystemService ( NOTIFICATION_SERVICE );
            man.createNotificationChannel ( x );
        }
    }

    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
         myData=getApplicationContext().openOrCreateDatabase("Employee",MODE_PRIVATE,null);
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        if (intent.getAction ().equals ("insert")){

         id = intent.getStringExtra("id");
         name = intent.getStringExtra("name");
         sex = intent.getStringExtra("sex");
         sale = intent.getStringExtra("sale");
         salary = intent.getStringExtra("salary");
         rate = intent.getStringExtra("rate");
         myData.execSQL("insert into t1(name,salary,sales,rate,sex,UserID)values('"+name+"','"+salary+"','"+sale+"'" + ",'"+rate+"','"+ sex +"','"+id+"')");
            NotificationCompat.Builder notefor = null;
            notefor = new NotificationCompat.Builder(getApplicationContext(), ChannelID)
                    .setAutoCancel(true)
                    .setContentTitle("Insert an Employee")
                    .setContentText("insert into t1(name,salary,sales,rate,sex,UserID)values('\"+name+\"','\"+salary+\"','\"+sale+\"'\" + \",'\"+rate+\"','\"+ sex +\"','\"+id+\"')\"")
                    .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                    .setOngoing(true)
                    .setColor(Color.BLUE)
                    .setUsesChronometer(true);
            startForeground(1, notefor.build());
         myData.close();

    }
        else if(intent.getAction ().equals ("delete")){
            id = intent.getStringExtra("id");
            myData.execSQL("delete from t1 where UserID=" + id);
            NotificationCompat.Builder notefor = null;
            notefor = new NotificationCompat.Builder(getApplicationContext(), ChannelID)
                    .setAutoCancel(true)
                    .setContentTitle("Delete an Employee")
                    .setContentText("\"delete from t1 where UserID=\" + id")
                    .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                    .setOngoing(true)
                    .setColor(Color.BLUE)
                    .setUsesChronometer(true);
            startForeground(1, notefor.build());
            myData.close();
        }
        else if(intent.getAction ().equals ("modify")){
            id = intent.getStringExtra("id");
            name = intent.getStringExtra("name");
            sex = intent.getStringExtra("sex");
            sale = intent.getStringExtra("sales");
            salary = intent.getStringExtra("salary");
            rate = intent.getStringExtra("rate");
            ContentValues cv = new ContentValues();
            cv.put("name", name);
            cv.put("salary", salary);
            cv.put("sales", sale);
            cv.put("rate", rate);
            cv.put("sex", sex);
            myData.update("t1", cv, "UserID="+ id, null);
            NotificationCompat.Builder notefor = null;
            notefor = new NotificationCompat.Builder(getApplicationContext(), ChannelID)
                    .setAutoCancel(true)
                    .setContentTitle("Modify an Employee")
                    .setContentText("update t1, cv, where UserID = id")
                    .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                    .setOngoing(true)
                    .setColor(Color.BLUE)
                    .setUsesChronometer(true);
            startForeground(1, notefor.build());
            myData.close();
            }



        else if(intent.getAction ().equals ("search")){
            id = intent.getStringExtra("id");
            Cursor c=myData.query("t1",null,null,null,null,null,null);

            if(c.getCount()==0)
            {
                Toast.makeText(getApplicationContext(),"ID is Not Found",Toast.LENGTH_LONG).show();
            }
            c.moveToFirst();
            for(int i=0;i<c.getCount();i++)
            {
                if(id.equals(c.getString(5))) {
                    name = c.getString(0);
                    salary = c.getString(1);
                    sale = c.getString(2);
                    rate = c.getString(3);
                    sex = c.getString(4);
                    id = c.getString(5);
                   Result="  Name     Salary     Sale    Rate    Gender    ID    \n" +"  "+name+"     "+salary+"     "+sale+"    "+rate+"    "+sex+"    "+id+"    ";
                    NotificationCompat.Builder notefor = null;
                    notefor = new NotificationCompat.Builder(getApplicationContext(), ChannelID)
                            .setAutoCancel(true)
                            .setContentTitle("Search For Employee")
                            .setContentText("Select * from t1 where UserID = id")
                            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                            .setOngoing(true)
                            .setColor(Color.BLUE)
                            .setUsesChronometer(true);
                    startForeground(1, notefor.build());
                   Toast.makeText(getApplicationContext(),"DONE ..",Toast.LENGTH_LONG).show();
                    break;
                }
                c.moveToNext();
            }
            Intent i=new Intent();
            i.setAction("toSearch");
            i.putExtra("id",Result);
            sendBroadcast(i);
        }
        else if(intent.getAction().equals("Display2")) {
            Cursor c = myData.query("t1", null, null, null, null, null, null);

            if (c.getCount() == 0) {
                Toast.makeText(getApplicationContext(), "No Data to Display", Toast.LENGTH_LONG).show();
            }
            c.moveToFirst();
            for (int i = 0; i < c.getCount(); i++) {

                name = c.getString(0);
                salary = c.getString(1);
                sale = c.getString(2);
                rate = c.getString(3);
                sex = c.getString(4);
                id = c.getString(5);
                Result += "\n" + "  " + name + "      " + salary + "      " + sale + "     " + rate + "       " + sex + "    " + id ;
                c.moveToNext();
            }
                    NotificationCompat.Builder notefor = null;
                    notefor = new NotificationCompat.Builder(getApplicationContext(), ChannelID)
                            .setAutoCancel(true)
                            .setContentTitle("Display all Informations")
                            .setContentText("Select * from t1")
                            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                            .setOngoing(true)
                            .setColor(Color.BLUE)
                            .setUsesChronometer(true);
                    startForeground(1, notefor.build());

            Intent i=new Intent();
            i.setAction("toDisplay");
            i.putExtra("result",Result);

            sendBroadcast(i);
            Result="";
                }

             return START_NOT_STICKY;}}