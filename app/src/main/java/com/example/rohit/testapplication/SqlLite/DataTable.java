package com.example.rohit.testapplication.SqlLite;

public class DataTable {

    public static final String TABLE_NAME = "DataTable";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_1 = "name";
    public static final String COLUMN_2 = "gender";
    public static final String COLUMN_3 = "email";
    public static final String COLUMN_4 = "age";
    public static final String COLUMN_5 = "dob";
    public static final String COLUMN_6 = "imgUrl";
    public static final String COLUMN_7 = "phone";
    public static final String COLUMN_8 = "cell";
    public static final String COLUMN_9 = "location";

    // Create table SQL query
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                                                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                                                + COLUMN_1 + " TEXT,"
                                                + COLUMN_2 + " TEXT,"
                                                + COLUMN_3 + " TEXT,"
                                                + COLUMN_4 + " TEXT,"
                                                + COLUMN_5 + " TEXT,"
                                                + COLUMN_6 + " TEXT,"
                                                + COLUMN_7 + " TEXT,"
                                                + COLUMN_8 + " TEXT,"
                                                + COLUMN_9 + " TEXT"
                                                + ")";

    public DataTable(){

    }

}
