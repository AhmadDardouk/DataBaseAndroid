package com.example.database;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Delete extends AppCompatActivity implements View.OnClickListener {
    Button DeleteButton, BackButton;
    EditText EmployeeID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        DeleteButton=findViewById(R.id.DeleteButton);
        BackButton=findViewById(R.id.BackButton);
        BackButton.setOnClickListener(this);
        EmployeeID=findViewById(R.id.delete);
        DeleteButton.setOnClickListener(this);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        if(v==DeleteButton)
        {
            if(EmployeeID.getText().toString().length()==0)
            {
                Toast.makeText(getApplicationContext(),"you must enter an ID to Delete",Toast.LENGTH_LONG).show();
                return ;
            }
            String ID=EmployeeID.getText().toString();
            int i = Integer.parseInt(ID);
            SQLiteDatabase myData=getApplicationContext().openOrCreateDatabase("Employee",MODE_PRIVATE,null);
            Cursor c = null;
            try {
                c = myData.query("t1", null,
                        null, null, null, null, null);
                try {
                    Cursor cursor = null;
                    cursor= myData.rawQuery("select UserID from t1 where UserID="+i+"",null);
                    if(cursor.getCount()>0) {
                        Intent k=new Intent(this,MyService.class);
                        k.putExtra("id",EmployeeID.getText().toString());
                        k.setAction("delete");
                        startForegroundService(k);
                        Toast.makeText(getApplicationContext(), "the Employee with ID : "+i+" is Deleted Successfully", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "No such Employee have this ID", Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "the row is not found", Toast.LENGTH_LONG).show();
                }
                }


            catch (Exception e) {Toast.makeText(getApplicationContext(),"no such table or row to delete",Toast.LENGTH_LONG).show();}
        }
        if(v==BackButton)
        {
            super.onBackPressed();
        }
    }
}
