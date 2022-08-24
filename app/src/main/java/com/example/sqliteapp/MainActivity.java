package com.example.sqliteapp;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    DataBaseHelper myDb;
    EditText editName, editSurname, editMarks, editTextId;
    Button btnAddData;
    Button btnviewAll;
    Button btnviewUpdate;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb=new DataBaseHelper(this);
        editName = (EditText) findViewById(R.id.editText_name);
        editSurname = (EditText) findViewById(R.id.editText_surname);
        editMarks = (EditText) findViewById(R.id.editText_Marks);
        editTextId = (EditText) findViewById(R.id.editText_ID);
        btnAddData = (Button) findViewById(R.id.button_add);
        btnviewAll = (Button) findViewById(R.id.button_vall);
        btnviewUpdate = (Button) findViewById(R.id.button_update);
        btnDelete = (Button) findViewById(R.id.button_delete);
        AddData();
        viewAll();
        UpdateData();
        DeleteData();
    }
    public void DeleteData(){
        btnDelete.setOnClickListener(
                view -> {
                    Integer deletedRows = myDb.deleteData(editTextId.getText().toString());
                    if (deletedRows>0)
                        Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(MainActivity.this, "Data not Deleted", Toast.LENGTH_LONG).show();
                }
        );
    }
    public void UpdateData(){
        btnviewUpdate.setOnClickListener(
                v -> {
                    boolean isUpdated = myDb.updateData(editTextId.getText().toString(), editName.getText().toString(), editSurname.getText().toString(), editMarks.getText().toString());
              if(isUpdated)Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
              else
                  Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_LONG).show();
                }
        );
    }
    public void AddData(){
        btnAddData.setOnClickListener(
                v -> {
                   boolean isInserted = myDb.insertData(editName.getText().toString(),
                            editSurname.getText().toString(),
                            editMarks.getText().toString());
                   if (isInserted)
                       Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                   else
                       Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();

                }
        );
    }
    public void viewAll (){
        btnviewAll.setOnClickListener(
                v -> {
                    Cursor res = myDb.getAllData();
                    if(res.getCount() == 0){
                        showMessage("Error", "No Data Found");
                    }
                    StringBuilder buffer = new StringBuilder();
                    while(res.moveToNext()){
                        buffer.append("Id :").append(res.getString(0)).append("\n");
                        buffer.append("Name :").append(res.getString(1)).append("\n");
                        buffer.append("SurName :").append(res.getString(2)).append("\n");
                        buffer.append("Marks :").append(res.getString(3)).append("\n \n");

                    }
                    showMessage("Data", buffer.toString());
                }
        );
    }
    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}