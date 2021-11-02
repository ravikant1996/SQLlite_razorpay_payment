package com.example.registration;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SQLLiteSample {
    Context context;

    public SQLLiteSample(Context context) {
        this.context = context;
    }

    public void create_db() {
        SQLiteDatabase db_connect = context.openOrCreateDatabase("MyDatabase", MODE_PRIVATE, null);

        db_connect.execSQL("create table if not exists table1 (id integer primary key autoincrement, name varchar, email varchar, phone varchar)");

        db_connect.close();
    }

    public void write_db() {
        SQLiteDatabase db_connect = context.openOrCreateDatabase("MyDatabase", MODE_PRIVATE, null);
        Cursor db_cursor;
//                            db_cursor = db_connect.rawQuery("select * from table1", null);
//                            int rows = 0;
//                            if (db_cursor != null) {
//                                rows = db_cursor.getCount();
//                            }
//                            rows++;

//                            db_connect.execSQL("insert into table1 values ('" + rows + ", " + _name + ", "
//                                    + _email + ", " + _phone + "')");
//        ContentValues values = new ContentValues();
//        values.put("id", rows);
//        values.put("name", _name);
//        values.put("email", _email);
//        values.put("phone", _phone);
//        values.put("uid", System.currentTimeMillis() + "");
//        db_connect.insert("table1", null, values);
//        db_connect.close();
//        Toast.makeText(Insert.this, "Data Inserted", Toast.LENGTH_SHORT).show();
    }

    public void read_db() {
        SQLiteDatabase db_connect = context.openOrCreateDatabase("MyDatabase", MODE_PRIVATE, null);
        Cursor db_cursor;
        db_cursor = db_connect.rawQuery("select * from table1", null);
        if (db_cursor != null) {
            db_cursor = db_connect.rawQuery("select * from table1", null);

            // on below line we are creating a new array list.
//            ArrayList<Info> listArray = new ArrayList<>();
//
//            // moving our cursor to first position.
//            if (db_cursor.moveToFirst()) {
//                do {
//                    listArray.add(new Info(
//                            db_cursor.getInt(0),
//                            db_cursor.getString(1),
//                            db_cursor.getString(2),
//                            db_cursor.getString(3),
//                            db_cursor.getString(4)));
//                } while (db_cursor.moveToNext());
//            }
            db_cursor.close();
            db_connect.close();


        } else {
//            Toast.makeText(MainActivity.this, "Empty" +
//                    "", Toast.LENGTH_SHORT).show();
        }
    }
}
