package com.example.registration;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelpher extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "student";
    private static final int DATABASE_VERSION = 1;
    private static final int DATABASE_VERSION_UPGRADE = 2;
    public static final String STUDENT_TABLE = "stureg";
    private static final String STU_TABLE = "create table " + STUDENT_TABLE + "(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, name TEXT,age TEXT, date TEXT , doctorName TEXT, description TEXT)";
    public static final String _ID = "id";
    static final String Title_KEY = "title";
    static final String Name_KEY = "name";
    static final String Age_KEY = "age";
    static final String Date_KEY = "date";
    static final String docName_KEY = "doctorName";
    static final String des_KEY = "description";
    Context context;

    public DatabaseHelpher(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION_UPGRADE);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(STU_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + STUDENT_TABLE);
        if (newVersion > oldVersion) {
            db.execSQL("ALTER TABLE STUDENT_TABLE ADD COLUMN dob TEXT");
        }
        // Create tables again
        onCreate(db);
    }

    /* Insert into database*/
    public void insertIntoDB(String title, String name, String age, String date, String doctorName, String description) {
        Log.d("insert", "before insert");

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        //  values.put("id", id);
        values.put("title", title);
        values.put("name", name);
        values.put("age", age);
        values.put("date", date);
        values.put("doctorName", doctorName);
        values.put("description", description);

        // 3. insert
        db.insert(STUDENT_TABLE, null, values);
        // 4. close
        db.close();
        Toast.makeText(context, "insert value", Toast.LENGTH_LONG).show();
        Log.i("insert into DB", "After insert");
    }

    /*
        public List<DatabaseModel> getAllDatas() {
            List<DatabaseModel> Datas = new ArrayList<>();

            // Select All Query
            String selectQuery = "SELECT  * FROM " + STUDENT_TABLE + " ORDER BY " + _ID + " ASC ";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    DatabaseModel data = new DatabaseModel();
                    DatabaseModel.setId(cursor.getInt(cursor.getColumnIndex(_ID)));
                  //  data.setId(cursor.getString(cursor.getColumnIndex(KEY_ID)));
                    DatabaseModel.setTitle(cursor.getString(cursor.getColumnIndex(Title_KEY)));
                    DatabaseModel.setName(cursor.getString(cursor.getColumnIndex(KEY_Name)));
                    DatabaseModel.setAge(cursor.getString(cursor.getColumnIndex(KEY_Age)));
                    DatabaseModel.setDate(cursor.getString(cursor.getColumnIndex(KEY_Date)));
                    DatabaseModel.setDoc(cursor.getString(cursor.getColumnIndex(KEY_docName)));
                    DatabaseModel.setDes(cursor.getString(cursor.getColumnIndex(KEY_des)));
                    Datas.add(data);
                } while (cursor.moveToNext());
            }
            // close db connection
            db.close();
            // return notes list
            return Datas;


        }

     */
    /* Retrive  data from database */
    public List<DatabaseModel> getDataFromDB() {
        List<DatabaseModel> modelList = new ArrayList<DatabaseModel>();
        String query = "select * from " + STUDENT_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);


        if (cursor.moveToFirst()) {
            do {
                DatabaseModel model = new DatabaseModel();
                //  model.setId(cursor.getString(0));
                model.setId(Integer.parseInt(cursor.getString(0)));
                model.setTitle(cursor.getString(1));
                model.setName(cursor.getString(2));
                model.setDate(cursor.getString(3));
                model.setAge(cursor.getString(4));
                model.setDoc(cursor.getString(5));
                model.setDes(cursor.getString(6));

                modelList.add(model);
            } while (cursor.moveToNext());
        }


        Log.d("student data", modelList.toString());


        return modelList;
    }

   /*
   //deleting row-------
    */

}
