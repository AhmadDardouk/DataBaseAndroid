package com.example.database;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Arrays;

public class Modify extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {
    EditText id,name,sale,salary,rate,ID;
    RadioButton male,female,radioSexButton;
    RadioGroup Rb;
    Button Back,Modify;
    String names[],sales[],salaries[],rates[],gender[],IDS[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SQLiteDatabase myData=getApplicationContext().openOrCreateDatabase("Employee",MODE_PRIVATE,null);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        id=findViewById(R.id.editID);
        id.setOnFocusChangeListener(this);
        name=findViewById(R.id.editName);
        sale=findViewById(R.id.editSale);
        salary=findViewById(R.id.editSalary);
        rate=findViewById(R.id.editRate);
        male=findViewById(R.id.male);
        female=findViewById(R.id.female);
        Rb=findViewById(R.id.editGender);
        female.setClickable(true);
        male.setClickable(true);
        Rb.setClickable(true);
        Rb.setActivated(true);
        Back=findViewById(R.id.back2);
        Modify=findViewById(R.id.modify);
        Back.setOnClickListener(this);
        Modify.setOnClickListener(this);
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
        if(v==Modify)
        {
            if(id.getText().toString().length()==0)
            {
                id.setHint("required");
                Toast.makeText(getApplicationContext(),"The ID is required",Toast.LENGTH_LONG).show();

                return;
            }
            Float a=Float.parseFloat(id.getText().toString());
            int index = -1;
            for(int i=0; i<IDS.length; i++){
                if (Integer.parseInt(IDS[i]) == a) {
                    index = i;
                    break;
                }
            }
            if(index==-1)
            {
                Toast.makeText(getApplicationContext(), "the id is not exist , try another one", Toast.LENGTH_LONG).show();

                return;
            }
            int selectedId = Rb.getCheckedRadioButtonId();
                radioSexButton = (RadioButton) findViewById(selectedId);
                if (name.getText().toString().length() == 0) {
                    name.setText(names[index]);
                }
                if (sale.getText().toString().length() == 0) {
                    sale.setText(sales[index]);
                }
                if (salary.getText().toString().length() == 0) {
                    salary.setText(salaries[index]);
                }
                if (rate.getText().toString().length() == 0) {
                    rate.setText(rates[index]);
                }

                 Intent intent=new Intent(this,MyService.class);
                 intent.putExtra("name", name.getText().toString());
                 intent.putExtra("salary", salary.getText().toString());
                 intent.putExtra("sales", sale.getText().toString());
                 intent.putExtra("rate", rate.getText().toString());
                 intent.putExtra("sex", radioSexButton.getText().toString());
                 intent.putExtra("id", id.getText().toString());
                 intent.setAction("modify");
                 startForegroundService(intent);
                Toast.makeText(getApplicationContext(), "Information updated successfully", Toast.LENGTH_LONG).show();
        }
        if(v==Back)
        {
            onBackPressed();
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(v==id)
        {
            if(hasFocus==false)
            {
                if (id.getText().toString().length() == 0) {
                    id.setHint("required");
                }
                if (id.getText().toString().length() != 0) {
                    int a = Integer.parseInt(id.getText().toString());
                    int index = -1;
                    for(int i=0; i<IDS.length; i++){
                        if (Integer.parseInt(IDS[i]) == a) {
                            index = i;
                            break;
                        }
                    }
                    if(index==-1)
                    {
                        Toast.makeText(getApplicationContext(), "ID is not Found", Toast.LENGTH_LONG).show();
                        return;
                    }
                   Toast.makeText(getApplicationContext(), Integer.toString(index+1), Toast.LENGTH_LONG).show();
                    name.setText("");
                    sale.setText("");
                    salary.setText("");
                    rate.setText("");

                    name.setHint(names[index]);
                    sale.setHint(sales[index]);
                    salary.setHint(salaries[index]);
                    rate.setHint(rates[index]);

                    if (gender[index].equals("F")) {
                        female.setChecked(true);
                    } else male.setChecked(true);
                }
            }
        }
    }
}