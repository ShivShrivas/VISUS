package com.org.visus.activity.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class VISUS_SQLiteHelper extends SQLiteOpenHelper {
    private static final int version = 1;
    private static final String dbName = "visus.db";
    public static String tblMyAction = "tblMyAction";

    public static String common_id = "common_id";
    public static String myAction_PhotoID = "myAction_PhotoID";
    public static String myAction_PhotoDescription = "myAction_PhotoDescription";
    public static String myAction_PhotoPath = "myAction_PhotoPath";
    public static String myAction_isSynced = "myAction_isSynced";

    private final String create_tblMyAction = "create table " + tblMyAction + " (" +
            common_id + " integer primary key autoincrement ," +
            myAction_PhotoID + " integer," +
            myAction_PhotoDescription + " text," +
            myAction_PhotoPath + " text," +
            myAction_isSynced + " text );";


    public VISUS_SQLiteHelper(Context context) {
        super(context, dbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(create_tblMyAction);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
