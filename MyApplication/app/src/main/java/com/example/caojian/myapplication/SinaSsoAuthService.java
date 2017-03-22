package com.example.caojian.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by caojian on 2016/10/29.
 */
public class SinaSsoAuthService extends Service {

    SinaSsoImpl mBinder = new SinaSsoImpl();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("","### sso auth create");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    class SinaSsoImpl extends SsoAyth.Stub{

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void ssoAyth(String userName, String pwd) throws RemoteException {
            Log.e("","这里是新浪客户端，执行SSO登陆"+userName+pwd);
        }


    }
}
