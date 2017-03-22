package com.example.caojian.myapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    SsoAyth mAyth = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_sso).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAyth == null){
                    bindSsoService();
                }else {

                    doSsoAuth();
                }
            }
        });
    }

    private void bindSsoService() {
        Intent intent = new Intent("book.aidl_server.server.SinaSsoAuthService");
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
    }
    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //建立连接后将Binder 转换成 Sso
            mAyth = SsoAyth.Stub.asInterface(service);
            doSsoAuth();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            mAyth = null;
        }
    };

    private void doSsoAuth() {
        try {
            mAyth.ssoAyth("caojian","123456");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
}
