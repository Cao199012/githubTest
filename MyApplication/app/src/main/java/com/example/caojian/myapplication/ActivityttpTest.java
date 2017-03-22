package com.example.caojian.myapplication;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caojian.myapplication.recyclerView.Fruit;
import com.example.caojian.myapplication.recyclerView.FruitAdapter;
import com.example.caojian.myapplication.service.MyService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class ActivityttpTest extends AppCompatActivity implements View.OnClickListener {

    private TextView mTvShow;
    private MyService.MyBinder myBinder;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start:
                myBinder.startLoad("https://raw.githubusercontent.com/guolindev/eclipse/master/eclipse-inst-win64.exe");
                break;
            case R.id.btn_pause:
                myBinder.pauseLoad();
                break;
            case R.id.btn_cancle:
                myBinder.cancleLoad();
                break;
            default:
                break;
        }
    }

    class MyConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (MyService.MyBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activityttp_test);
        Intent intent = new Intent(ActivityttpTest.this, MyService.class);
        bindService(intent,new MyConnection(),BIND_AUTO_CREATE);

        mTvShow = (TextView) findViewById(R.id.tv_http);
        Button btn_put = (Button) findViewById(R.id.btn_http);
        Button btn_start = (Button) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);
        Button btn_pause = (Button) findViewById(R.id.btn_pause);
        btn_pause.setOnClickListener(this);
        Button btn_cancle = (Button) findViewById(R.id.btn_cancle);
        btn_cancle.setOnClickListener(this);
        btn_put.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRequestHttp();


            }
        });
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ActivityttpTest.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar
        );

        FloatingActionButton myFloat = (FloatingActionButton) findViewById(R.id.my_float);
        myFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"delete",Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(ActivityttpTest.this,"aaaa",Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recy);
        FruitAdapter fruitAdapter = new FruitAdapter(createList());
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(fruitAdapter);
    }

    private List<Fruit> createList() {
        List<Fruit>  list = new ArrayList<>();
        for (int i = 0; i < 14; i++){
            Fruit fruit = new Fruit();
            fruit.setText_msg(i+"aaaa");
            list.add(fruit);
        }
        return list;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED ){
                    finish();
                }
                break;
            default:
                break;
        }
    }

    private void setRequestHttp() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL _url = new URL("http://www.baidu.com");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) _url.openConnection();
                    httpURLConnection.setConnectTimeout(8000);  //设置连接超时时间
                    InputStream in = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder builder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null){
                        builder.append(line);
                    }
                    showRequest(builder);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    // TODO: 2017/2/22 关闭 httpURLConnection 和 bufferedReader
                }
            }
        }).start();
    }

    private void showRequest(final StringBuilder builder) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTvShow.setText(builder);
            }
        });
    }
}
