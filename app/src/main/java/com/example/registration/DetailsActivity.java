package com.example.registration;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DetailsActivity extends AppCompatActivity  {
    DatabaseHelpher helpher;
    List<DatabaseModel> dbList;
    public int position;
    int position1;
    TextView tvtitle;
    TextView tvname;
    TextView tvdate;
    TextView tvage;
    TextView tvdoctorName;
    TextView tvdescription;
    private TextView editTextid;
    private Context context;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Details about Person");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        // 5. get status value from bundle
         position1 = bundle.getInt("position1");

 //       Bundle extra = getIntent().getExtras();
   //     int pos = extra.getInt("pos");


    //    editTextid= (TextView)findViewById(R.id.idNo);
        tvtitle =(TextView)findViewById(R.id.title);
        tvname =(TextView)findViewById(R.id.name);
        tvage =(TextView)findViewById(R.id.age);
        tvdate =(TextView)findViewById(R.id.date);
        tvdoctorName =(TextView)findViewById(R.id.doctorName);
        tvdescription =(TextView)findViewById(R.id.description);
 //       Button btnUpdate = (Button) findViewById(R.id.buttonEdit);
   //     Button btnDelete = (Button) findViewById(R.id.buttonDelete);

        helpher = new DatabaseHelpher(this);
        dbList= new ArrayList<>();
        dbList = helpher.getDataFromDB();

        if(dbList.size()>0){

            String title=dbList.get(position1).getTitle();
            String name= dbList.get(position1).getName();
            String age=dbList.get(position1).getAge();
            String date=dbList.get(position1).getDate();
            String docName=dbList.get(position1).getDoc();
            String desc=dbList.get(position1).getDes();
            tvtitle.setText("Title: " + title);
            tvname.setText("Name: " + name);
            tvage.setText("Age: " + age);
            tvdate.setText("Date: " + date);
            tvdoctorName.setText("Doctor Name: " + docName);
            tvdescription.setText("Description: " + desc);

        }
       // Toast.makeText(DetailsActivity.this, dbList.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
