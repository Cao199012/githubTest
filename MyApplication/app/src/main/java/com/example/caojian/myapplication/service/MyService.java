package com.example.caojian.myapplication.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.caojian.myapplication.ActivityttpTest;
import com.example.caojian.myapplication.R;

/**
 * Created by caojian on 2017/2/27.
 */
public class MyService extends Service {



    private DownTask mDownTask;

    private DownloadListen myListen = new DownloadListen() {
        @Override
        public void onProgress(int progress) {
            getNotiMange().notify(1,getNotification("DownLoading...",progress));
        }

        @Override
        public void onSuccess() {
            mDownTask =null;
            stopForeground(true);
            getNotiMange().notify(1,getNotification("DownLoad Success",100));
        }

        @Override
        public void onDailed() {
            mDownTask =null;
            stopForeground(true);
            getNotiMange().notify(1,getNotification("DownLoad Failed",-1));
        }

        @Override
        public void onPused() {
            mDownTask = null;
            Toast.makeText(MyService.this,"Paused",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancle() {
            mDownTask = null;

        }
    };

    private Notification getNotification(String title,int progress) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        Intent intent = new Intent(this,ActivityttpTest.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        builder.setContentTitle(title);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(pendingIntent);
        if(progress > 0){
            builder.setContentText("已下载"+progress+"%");
            builder.setProgress(100,progress,true);
        }
        return builder.build();
    }

    private NotificationManager getNotiMange() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Intent intent1 = new Intent(this, ActivityttpTest.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this,1,intent,0);
//        Notification notification = new NotificationCompat.Builder(this).build();
        return flags;
    }

    public class  MyBinder extends Binder{
        public String getMsg(){
         return "aaaa";
        }

        public void startLoad(String url){
            if(mDownTask == null) {
                mDownTask = new DownTask(myListen);
                mDownTask.execute(url);
                startForeground(1,getNotification("DowbLoad...",0));
            }

        }

        public void pauseLoad(){
            if(mDownTask != null){
                mDownTask.setPause();
            }

        }

        public void cancleLoad(){
            if(mDownTask != null) {
                mDownTask.setCancle();
            }
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }
}
