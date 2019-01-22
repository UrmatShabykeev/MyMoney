package com.example.user.mymoney;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper_Incomes extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "IncomesDB1";
    public static final String TABLE_INCOMES = "table_of_incomes";

    public static final String KEY_ID = "_id";
    public static final String KEY_INCOME = "income";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_DAY = "day";
    public static final String KEY_MONTH = "month";
    public static final String KEY_YEAR = "year";

    public DBHelper_Incomes(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table " + TABLE_INCOMES + "(" + KEY_ID + " integer primary key," + KEY_INCOME + " integer," + KEY_CATEGORY + " text," + KEY_DESCRIPTION + " text," + KEY_DAY + " integer,"
                + KEY_MONTH + " text," + KEY_YEAR + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop table if exists " + TABLE_INCOMES);
        onCreate(db);
    }
}
