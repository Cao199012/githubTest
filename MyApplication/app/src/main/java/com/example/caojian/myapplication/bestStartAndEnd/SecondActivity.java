package com.example.caojian.myapplication.bestStartAndEnd;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.caojian.myapplication.R;

public class SecondActivity extends AppCompatActivity {

    public static void jump2Second(Context fActivity){
        Intent intent = new Intent(fActivity,SecondActivity.class);//没onCreate 所以不能用this
        intent.putExtra("","");
        fActivity.startActivity(intent);//
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activityecond);
    }
}
