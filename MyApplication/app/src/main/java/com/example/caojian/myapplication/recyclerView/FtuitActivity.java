package com.example.caojian.myapplication.recyclerView;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.caojian.myapplication.R;

public class FtuitActivity extends AppCompatActivity {

    private TextView mTv_fruitName;
    public static void toFruitActivity(Context fromclass, String msg){

        Intent intent = new Intent(fromclass,FtuitActivity.class);
        intent.putExtra("msg",msg);
        fromclass.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ftuit);
        init();
    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.fruit_toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collp);
        collapsingToolbarLayout.setTitle("曹健测试");
        mTv_fruitName = (TextView) findViewById(R.id.tv_fruit);
        generateMsg(getIntent().getStringExtra("msg"));

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void generateMsg(String msg) {
        for (int i = 0; i < 50; i++){
            mTv_fruitName.append(msg);
        }
    }
}
