package com.example.caojian.myapplication.contentProcider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by caojian on 2016/11/7.
 */
public class UserInfoProvider extends ContentProvider {
    SQLiteDatabase mDatabase;
    private static String COTENT = "conyent://";
    private static String AUTHORY = "com.caojian";


    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd" + AUTHORY;
    public static final String CONTENT_TYPE_ITEM = "vnd.android.cursor.item/vnd" +AUTHORY;

    public static final Uri POSTCODE_URL = Uri.parse(COTENT+AUTHORY+"/"+UserInfoDbHelper.TABLE_USERINFO);
    public static final Uri COMPANY_URL = Uri.parse(COTENT+AUTHORY+"/"+UserInfoDbHelper.TABLE_COMPANY);

    static final int USER_INFOS = 1;
    static final int USER_INFOS_ITEM = 2;
    static final int COMPANY = 3;
    static final int COMPANY_ITEM = 4;

    static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(AUTHORY,"userinfo",USER_INFOS);
        uriMatcher.addURI(AUTHORY,"userinfo/*",USER_INFOS_ITEM);
        uriMatcher.addURI(AUTHORY,"company",COMPANY);
        uriMatcher.addURI(AUTHORY,"company/#",COMPANY_ITEM);
    }

    @Override
    public boolean onCreate() {
        mDatabase = new UserInfoDbHelper(getContext()).getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        switch (uriMatcher.match(uri)){
            case USER_INFOS:
                String tel = uri.getPathSegments().get(1);
                cursor = mDatabase.query(UserInfoDbHelper.TABLE_USERINFO,projection,"tel_num = ?",new String[]{tel},null,null,sortOrder);
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case USER_INFOS:
            case COMPANY:
                return CONTENT_TYPE;
            case USER_INFOS_ITEM:
            case COMPANY_ITEM:
                return CONTENT_TYPE_ITEM;
            default:
                throw new RuntimeException("错误的 URL");
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long newId = 0;
        Uri newUrl = null;
        switch (uriMatcher.match(uri)){
            case USER_INFOS:
                newId = mDatabase.insert(UserInfoDbHelper.TABLE_USERINFO,null,values);
                newUrl = Uri.parse(COTENT+AUTHORY+"/"+UserInfoDbHelper.TABLE_USERINFO+"/"+newId);
                break;
            case COMPANY:
                newId = mDatabase.insert(UserInfoDbHelper.TABLE_COMPANY,null,values);
                newUrl = Uri.parse(COTENT+AUTHORY+"/"+UserInfoDbHelper.TABLE_COMPANY+"/"+newId);
                break;
            default:
                break;
        }

        if(newId > 0){
            return newUrl;
        }
        throw new IllegalArgumentException("Failed to insert");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
