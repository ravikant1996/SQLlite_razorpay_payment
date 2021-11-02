package com.example.registration;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.AbstractCursor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.registration.DatabaseHelpher.Age_KEY;
import static com.example.registration.DatabaseHelpher.Date_KEY;
import static com.example.registration.DatabaseHelpher.Name_KEY;
import static com.example.registration.DatabaseHelpher.STUDENT_TABLE;
import static com.example.registration.DatabaseHelpher.Title_KEY;
import static com.example.registration.DatabaseHelpher._ID;
import static com.example.registration.DatabaseHelpher.des_KEY;
import static com.example.registration.DatabaseHelpher.docName_KEY;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {
    Button updatebutton;
    EditText Title, Name, Age, Date, DoctorName, Description ;
    DatabaseHelpher helpher;
    List<DatabaseModel> dbList;
    SQLiteDatabase db;
    String str_position;
    int position1;
    Cursor c1;

    private DatePickerDialog fromDatePickerDialog;

    private SimpleDateFormat dateFormatter;
    private Cursor cursor;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        updatebutton = findViewById(R.id.button3);
        //    Id = findViewById(R.id.ID);
        Title = findViewById(R.id.TitleText);
        Name = findViewById(R.id.editText1);
        Age = findViewById(R.id.editText2);
        Date = findViewById(R.id.editText3);
        DoctorName = findViewById(R.id.editText4);
        Description = findViewById(R.id.editText5);

        helpher = new DatabaseHelpher(this);
        db = helpher.getWritableDatabase();
        final int rowId = getIntent().getIntExtra("USERID", -1);
        c1 = db.query(STUDENT_TABLE, null, _ID + " = " + rowId, null, null, null, null);

        dbList= new ArrayList<>();
        dbList.clear();
           if (c1 != null && c1.getCount() != 0) {
            while (c1.moveToNext()) {
                Title.setText(c1.getString(c1.getColumnIndex(Title_KEY)));
                Name.setText(c1.getString(c1.getColumnIndex(Name_KEY)));
                Age.setText(c1.getString(c1.getColumnIndex(Age_KEY)));
                Date.setText(c1.getString(c1.getColumnIndex(Date_KEY)));
                DoctorName.setText(c1.getString(c1.getColumnIndex(docName_KEY)));
                Description.setText(c1.getString(c1.getColumnIndex(des_KEY)));
            }
        }
        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = Title.getText().toString();
                String name = Name.getText().toString();
                String age = Age.getText().toString();
                String date = Date.getText().toString();
                String doctorName = DoctorName.getText().toString();
                String description = Description.getText().toString();

           //     db=this.getReadableDatabse();
                ContentValues values = new ContentValues();
                values.put(Title_KEY, title);
                values.put(Name_KEY, name);
                values.put(Age_KEY, age);
                values.put(Date_KEY, date);
                values.put(docName_KEY, doctorName);
                values.put(des_KEY, description);

                int updateId = db.update(STUDENT_TABLE, values, _ID + " = " + rowId, null);
                if (updateId != -1) {
                    Toast.makeText(EditActivity.this, "User Details Upated succesfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {

                    Toast.makeText(EditActivity.this, "User Updation Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        findViewsById();

        setDateTimeField();
    }


    /*
    @Override
    protected void onResume() {

        ShowSRecordInEditText();

        super.onResume();
    }

    public void ShowSRecordInEditText() {

        db = helpher.getWritableDatabase();

        String IDholder = getIntent().getStringExtra("USERID");

        c1 = db.rawQuery("SELECT * FROM demoTable WHERE ID = "+IDholder+"", null);

        if (c1.moveToFirst()) {

            do {
                Title.setText(c1.getString(c1.getColumnIndex(DatabaseHelpher.Title_KEY)));
                Name.setText(c1.getString(c1.getColumnIndex(DatabaseHelpher.Name_KEY)));
                Age.setText(c1.getString(c1.getColumnIndex(DatabaseHelpher.Age_KEY)));
                Date.setText(c1.getString(c1.getColumnIndex(DatabaseHelpher.Date_KEY)));
                DoctorName.setText(c1.getString(c1.getColumnIndex(DatabaseHelpher.docName_KEY)));
                Description.setText(c1.getString(c1.getColumnIndex(DatabaseHelpher.des_KEY)));
            }
            while (cursor.moveToNext());

            cursor.close();

        }
    }

     */
    /////  Date picker-------------->>>>
    private void findViewsById() {

        Date.setInputType(InputType.TYPE_NULL);
        Date.requestFocus();
    }

    private void setDateTimeField() {
        Date.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                Date.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view == Date) {
            fromDatePickerDialog.show();
        }
    }
    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }
}


