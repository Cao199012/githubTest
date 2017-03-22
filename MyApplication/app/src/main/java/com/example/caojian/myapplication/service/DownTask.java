package com.example.caojian.myapplication.service;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by caojian on 2017/3/2.
 */
public class DownTask extends AsyncTask<String,Integer,Integer> {
    private final static int TYPE_SUCCESS = 0;
    private final static int TYPE_PAUSE = 1;
    private final static int TYPE_CANCLE = 2;
    private final static int TYPE_FAILED = 3;
    private Boolean isCancle = false;
    private Boolean isPause = false;
    DownloadListen mDownloadListen;
    long lastProgress = 0;
    public DownTask(DownloadListen downloadListen){
        mDownloadListen = downloadListen;
    }
    @Override
    protected Integer doInBackground(String... params) {
        InputStream is = null;
        RandomAccessFile saveFile = null;

        long downLength = 0;
        String dowanUrl = params[0];
        String fileName = dowanUrl.substring(dowanUrl.lastIndexOf("/"));
        String directy = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
        File file = new File(directy+fileName);
        if(file.exists()){
            downLength = file.length();
        }

        long contentLength = getCotent(dowanUrl);

        if(downLength == contentLength){
            return TYPE_SUCCESS;
        }else if(contentLength == 0){
            return TYPE_FAILED;
        }
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .addHeader("RANG","bytes="+downLength+"-")
                .url(dowanUrl)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response != null && response.isSuccessful()){
                is = response.body().byteStream();
                saveFile = new RandomAccessFile(file,"rw");
                saveFile.seek(downLength);
                int total = 0; //记录读取的总字节数
                int len ; //记录每次读取的长度
                byte[] b = new byte[1024];
                while ((len = is.read(b)) != -1){
                    if (isCancle){
                        return TYPE_CANCLE;
                    }else if(isPause){
                        return TYPE_PAUSE;
                    }
                    total += len;
                    saveFile.write(b,0,len);
                    int precent = (int) ((total + downLength)*100/contentLength);
                    publishProgress(precent);
                }
                response.close();
                return TYPE_SUCCESS;

            }
        } catch (IOException e) {
            e.printStackTrace();

        }finally {
            try {
                if(is != null){
                    is.close();
                }
                if (saveFile != null){
                    saveFile.close();
                }

                if(file != null && isCancle){
                    file.delete();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return TYPE_FAILED;
    }

    private long getCotent(String dowanUrl) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(dowanUrl).build();
        long cotentLength = 0;
        try {
            Response response = client.newCall(request).execute();
            if(response != null && response.isSuccessful()) {
                cotentLength = response.body().contentLength();
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cotentLength;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        int _progress = values[0];
        if(_progress > lastProgress) {
            mDownloadListen.onProgress(_progress);
            lastProgress = _progress;
        }
    }

    @Override
    protected void onPostExecute(Integer integer) {
        switch (integer){
            case TYPE_SUCCESS:
                mDownloadListen.onSuccess();
                break;
            case TYPE_CANCLE:
                mDownloadListen.onCancle();
                break;
            case TYPE_PAUSE:
                mDownloadListen.onPused();
                break;
            case TYPE_FAILED:
                mDownloadListen.onDailed();
                break;
            default:
                break;
        }
    }

    public void setCancle(){
        isCancle = true;
    }

    public void setPause(){
        isPause = true;
    }
}
