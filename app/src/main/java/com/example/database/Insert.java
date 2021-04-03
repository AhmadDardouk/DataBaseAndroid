package com.example.database;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.nio.channels.SelectableChannel;

public class Insert extends AppCompatActivity implements View.OnClickListener {
    Button add, back;
    EditText Rate, Name,  Salary, Sales,ID;
    TextView t1, t2;
    RadioGroup RB;
    RadioButton M,F,radioSexButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert);
        RB=findViewById(R.id.RB);
        M=findViewById(R.id.r2);
        F=findViewById(R.id.r1);
        add = findViewById(R.id.add);
        Name = findViewById(R.id.Name);
        Rate = findViewById(R.id.Rate);
        add.setOnClickListener(this);
        back = findViewById(R.id.Back);
        Salary = findViewById(R.id.Salary);
        back.setOnClickListener(this);
        Sales = findViewById(R.id.Sales);
        t2 = findViewById(R.id.text2);
        ID=findViewById(R.id.EmpID);
    }




    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        if(v==back)
        {
            onBackPressed();
        }
        else if(v==add)
        {
            if(Name.getText().toString().length()==0||Sales.getText().length()==0||Salary.getText().length()==0||Rate.getText().length()==0||RB.getCheckedRadioButtonId()==-1||ID.getText().toString().length()==0)
            {
                Toast.makeText(getApplicationContext(),"you must fill up all fields ",Toast.LENGTH_LONG).show();
                return;
            }
            else{
            SQLiteDatabase myData=getApplicationContext().openOrCreateDatabase("Employee",MODE_PRIVATE,null);
            Cursor c = null;
            try
            {
                c = myData.query("t1", null,
                        null, null, null, null, null);
                int selectedId = RB.getCheckedRadioButtonId();
                radioSexButton = (RadioButton) findViewById(selectedId);
                String Query = "Select * from " + "t1" + " where " + "UserID" + " = " + ID.getText().toString();
                Cursor cursor = myData.rawQuery(Query, null);
                if(cursor.getCount() <= 0) {
                    cursor.close();
                    Intent i=new Intent(this,MyService.class);
                    i.putExtra("id",ID.getText().toString());
                    i.putExtra("salary",Salary.getText().toString());
                    i.putExtra("name",Name.getText().toString());
                    i.putExtra("sale",Sales.getText().toString());
                    i.putExtra("sex",radioSexButton.getText().toString());
                    i.putExtra("rate",Rate.getText().toString());
                    i.setAction("insert");
                    startForegroundService(i);










                        Toast.makeText(getApplicationContext(),"Data added successfully",Toast.LENGTH_LONG).show();
                        return;
                    }
                        cursor.close();
                    Toast.makeText(getApplicationContext(),"this id is already used , try another one",Toast.LENGTH_LONG).show();
                    return;
            }
            catch (Exception e) {
                myData.execSQL("create table t1(name varchar, salary float , sales float, rate float ,sex char,UserID float)");
                int selectedId = RB.getCheckedRadioButtonId();

                radioSexButton = (RadioButton) findViewById(selectedId);
                Intent i=new Intent(this,MyService.class);
                i.putExtra("id",ID.getText().toString());
                i.putExtra("salary",Salary.getText().toString());
                i.putExtra("name",Name.getText().toString());
                i.putExtra("sale",Sales.getText().toString());
                i.putExtra("sex",radioSexButton.getText().toString());
                i.putExtra("rate",Rate.getText().toString());
                i.setAction("insert");
                startService(i);

                Toast.makeText(getApplicationContext(),"Data added successfully",Toast.LENGTH_LONG).show();

            }



        }

    }}
}