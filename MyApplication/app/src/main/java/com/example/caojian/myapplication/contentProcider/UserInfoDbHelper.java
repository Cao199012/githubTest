package com.example.caojian.myapplication.contentProcider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by caojian on 2016/11/7.
 */
public class UserInfoDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "userinfo.db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_USERINFO = "userinfo";
    public static final String TABLE_COMPANY = "company";
    private static final String TEL_COLUMN = "tel_num";
    private static final String DESC_COLUMN = "desc_num";
    private static final String COMP_ID_COLUMN = "comp_id";
    private static final String ID_COLUMN = "id";
    private static final String ADDR_COLUMN = "addr";
    private static final String BUSINESS_COLUMN = "business";
    private static final String POSTCODE_TABLE_SQL = "CREATE_TABLE" + TABLE_USERINFO + " ("
            + TEL_COLUMN + " TEXT,"
            + DESC_COLUMN + " TEXT,"
            + COMP_ID_COLUMN + " TEXT"
            +")";
    private static final String COMPANY_TABLE_SQL = "CREATE_TABLE" + TABLE_COMPANY + " ("
            + ID_COLUMN + " TEXT,"
            + BUSINESS_COLUMN + " TEXT,"
            +  ADDR_COLUMN + " TEXT"
            +")";



    public UserInfoDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(POSTCODE_TABLE_SQL);
        db.execSQL(COMPANY_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
