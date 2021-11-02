package com.example.registration;


import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
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
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, PaymentResultListener {
    EditText etTitle, etName, etAge, etDoctorName, etDescrption, etDate;
    Button btnSubmit, btngetdata;
    DatabaseHelpher helpher;
    List<DatabaseModel> dbList;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbList = new ArrayList<DatabaseModel>();

        Checkout.preload(MainActivity.this);


        etTitle = (EditText) findViewById(R.id.etTitle);
        etName = (EditText) findViewById(R.id.etName);
        etAge = (EditText) findViewById(R.id.etAge);
        etDate = (EditText) findViewById(R.id.etDate);
        etDoctorName = (EditText) findViewById(R.id.etDoctorName);
        etDescrption = (EditText) findViewById(R.id.etDescription);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btngetdata = (Button) findViewById(R.id.btngetdata);

        permission();

        //date setting
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        findViewsById();
        setDateTimeField();

        btngetdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));

                // startActivity(new Intent(MainActivity.this, DetailsActivity.class));

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String name = etName.getText().toString();
                String age = etAge.getText().toString();
                String date = etDate.getText().toString();
                String doctorName = etDoctorName.getText().toString();
                String description = etDescrption.getText().toString();

                if (title.equals("") || name.equals("") || age.equals("") || date.equals("") || doctorName.equals("") || description.equals("")) {
                    Toast.makeText(MainActivity.this, "Please fill all the fields", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    helpher = new DatabaseHelpher(MainActivity.this);
                    helpher.insertIntoDB(title, name, age, date, doctorName, description);
                }

                payNow(etDate.getText().toString(), etName.getText().toString(), etTitle.getText().toString(), "" + 100);


                etTitle.setText("");
                etName.setText("");
                etAge.setText("");
                etDate.setText("");
                etDoctorName.setText("");
                etDescrption.setText("");


                Toast.makeText(MainActivity.this, "insert value", Toast.LENGTH_LONG).show();

            }
        });

    }

    private void payNow(String date, String name, String title, String amount) {
        final Activity activity = this;

        Checkout checkout = new Checkout();
        checkout.setKeyID("havsdjvawjsfbcjwabfjs");
        checkout.setImage(R.drawable.calender_foreground);

        double finalAMount = Float.parseFloat(amount) * 100;

        try {
            JSONObject options = new JSONObject();

            options.put("name", name);
            options.put("description", title + "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", finalAMount + "");//pass amount in currency subunits
            options.put("prefill.email", "gaurav.kumar@example.com");
            options.put("prefill.contact", date);
//            JSONObject retryObj = new JSONObject();
//            retryObj.put("enabled", true);
//            retryObj.put("max_count", 4);
//            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch (Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    private void permission() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]
                    {Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    private void findViewsById() {
        etDate.setInputType(InputType.TYPE_NULL);
        etDate.requestFocus();
    }

    private void setDateTimeField() {
        etDate.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etDate.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view == etDate) {
            fromDatePickerDialog.show();
        }
    }


    @Override
    public void onPaymentSuccess(String s) {
        btnSubmit.setText(s);
        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        btnSubmit.setText(s);
        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
    }
}
