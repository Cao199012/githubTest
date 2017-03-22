package com.example.caojian.myapplication.bestStartAndEnd;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by caojian on 2017/1/4.
 */
public class ActivityCollector {
    private static List<Activity> mCollector;
    static {
        if(mCollector == null){
            mCollector = new LinkedList<>();
        }
    }
    public static void addActivity(Activity activity){
        mCollector.add(activity);
    }

    public static void removeActivity(Activity activity){
        mCollector.remove(activity);
    }

    public static void finishAll(){
        for (Activity activity : mCollector){
            mCollector.remove(activity);
        }
    }
}
