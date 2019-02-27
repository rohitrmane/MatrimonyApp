package com.example.rohit.testapplication.SqlLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.rohit.testapplication.Model.UserProfile;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "DATA_DB";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DataTable.TABLE_NAME);
        onCreate(db);
    }


    public int deleteData() {
        String countQuery = " DELETE FROM "  +DataTable.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int deleteOneData(String phone) {
        String countQuery = " DELETE FROM " +DataTable.TABLE_NAME+ " WHERE "+ DataTable.COLUMN_7 +" = "+ "'"+ phone+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public long insertDataTable(String name, String gender, String email, String age, String dob, String imgUrl, String phone, String cell,String location) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(DataTable.COLUMN_1, name);
        values.put(DataTable.COLUMN_2, gender);
        values.put(DataTable.COLUMN_3, email);
        values.put(DataTable.COLUMN_4, age);
        values.put(DataTable.COLUMN_5, dob);
        values.put(DataTable.COLUMN_6, imgUrl);
        values.put(DataTable.COLUMN_7, phone);
        values.put(DataTable.COLUMN_8, cell);
        values.put(DataTable.COLUMN_9, location);
        // insert row
        long id = db.insert(DataTable.TABLE_NAME, null, values);
        // close db connection
        db.close();
        // return newly inserted row id
        return id;
    }


    public ArrayList<UserProfile> getDataTableDetails() {
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DataTable.TABLE_NAME ;
        ArrayList<UserProfile> documentList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex(DataTable.COLUMN_1));
                String gender = cursor.getString(cursor.getColumnIndex(DataTable.COLUMN_2));
                String email = cursor.getString(cursor.getColumnIndex(DataTable.COLUMN_3));
                String age = cursor.getString(cursor.getColumnIndex(DataTable.COLUMN_4));
                String dob = cursor.getString(cursor.getColumnIndex(DataTable.COLUMN_5));
                String imgUrl = cursor.getString(cursor.getColumnIndex(DataTable.COLUMN_6));
                String phone = cursor.getString(cursor.getColumnIndex(DataTable.COLUMN_7));
                String cell  = cursor.getString(cursor.getColumnIndex(DataTable.COLUMN_8));
                String location  = cursor.getString(cursor.getColumnIndex(DataTable.COLUMN_9));
                documentList.add(new UserProfile(name,gender,email,age,dob,imgUrl,phone,cell,location));
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();
        // return notes list
        return documentList;
    }


}
