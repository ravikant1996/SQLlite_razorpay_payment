package com.example.registration;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class createPDF extends AppCompatActivity {

    public DatabaseHelpher helpher;
    public List<DatabaseModel> dbList;
    public int position1;
    public SQLiteDatabase db;

    public TextView tvtitle;
    public TextView tvname;
    public TextView tvdate;
    public  TextView tvage;
    public  TextView tvdoctorName;
    public TextView tvdescription;
    public static final int REQUEST_PERM_WRITE_STORAGE = 102;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pdf);

        tvtitle = findViewById(R.id.title);
        tvname = findViewById(R.id.name);
        tvage = findViewById(R.id.age);
        tvdate = findViewById(R.id.date);
        tvdoctorName = findViewById(R.id.doctorName);
        tvdescription = findViewById(R.id.description);

        if(ActivityCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(createPDF.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERM_WRITE_STORAGE);
        }
        final int rowId = getIntent().getIntExtra("USERID", -1);
        helpher = new DatabaseHelpher(this);
        db = helpher.getWritableDatabase();


    //    ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

         String query="select * from stureg where id = " +rowId;

        Cursor c1 = db.rawQuery(query, null);
        try {
            c1.moveToFirst();
            tvtitle.setText(c1.getString(1));
            tvname.setText(c1.getString(2));
            tvage.setText(c1.getString(3));
            tvdate.setText(c1.getString(4));
            tvdoctorName.setText(c1.getString(5));
            tvdescription.setText(c1.getString(6));
        }
        catch (Exception e){
            e.printStackTrace();
            tvtitle.setText("");
            tvname.setText("");
            tvage.setText("");
            tvdate.setText("");
            tvdoctorName.setText("");
            tvdescription.setText("");
            return;
        }

        PdfDocument pdfDocument= new PdfDocument();
        PdfDocument.PageInfo pageInfo= new PdfDocument.PageInfo.Builder(595, 842,1).create();
        PdfDocument.Page page= pdfDocument.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        //circle------
        paint.setColor(Color.RED);
        canvas.drawCircle(50, 50, 30, paint);
        //line-------------
        paint.setColor(Color.RED);
        canvas.drawLine(20, 190,575, 190, paint);
        //signature-------------
        paint.setColor(Color.BLUE);
        canvas.drawText("Ravikant Yadav", 460, 775, paint);
        //------------values
        page.getCanvas().drawText("Title:- "+c1.getString(1), 20, 210, new Paint());
        page.getCanvas().drawText("Name:- "+c1.getString(2), 20, 230, new Paint());
        page.getCanvas().drawText("Age:- "+c1.getString(3), 20, 250, new Paint());
        page.getCanvas().drawText("Date:- "+c1.getString(4), 20, 270, new Paint());
        page.getCanvas().drawText("Doctor Name:- "+c1.getString(5), 20, 290, new Paint());
 //       page.getCanvas().drawText(c1.getString(6), 20, 300, new Paint());
        // description-------------multiline
        TextPaint mTextPaint=new TextPaint();
        StaticLayout mTextLayout = new StaticLayout("Description:- "+c1.getString(6), mTextPaint, canvas.getWidth(), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        canvas.save();
        int textX = 20;
        int textY = 310;
        canvas.translate(textX, textY);
        mTextLayout.draw(canvas);
        canvas.restore();

        pdfDocument.finishPage(page);

        c1.close();
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String filePath= Environment.getExternalStorageDirectory().getPath()+"/Download/"+tvname.getText().toString()+fileName+".pdf";
        File file= new File(filePath);

        try {
            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(this, "PDF is generated", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("main", "error "+e.toString());
            Toast.makeText(this, "Something wrong: " + e.toString(),  Toast.LENGTH_LONG).show();
        }
        pdfDocument.close();

     //   openGeneratedPDF();
    }
 /*   private void openGeneratedPDF(){
        File file = new File(Environment.getExternalStorageDirectory().getPath()+"/sdcard/"+tvname.getText().toString()+".pdf");
        if (file.exists())
        {
            Intent intent=new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try
            {
                startActivity(intent);
            }
            catch(ActivityNotFoundException e)
            {
                Toast.makeText(createPDF.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();

            }
        }
    }

  */
}


