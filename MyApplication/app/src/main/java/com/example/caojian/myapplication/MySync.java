package com.example.caojian.myapplication;

import android.os.AsyncTask;

/**
 * Created by caojian on 2017/2/26.
 */
public class MySync extends AsyncTask<Class,String,Boolean> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Class... params) {
        publishProgress();
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }
}
