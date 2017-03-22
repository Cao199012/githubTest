package com.example.caojian.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class CameraActivity extends AppCompatActivity {

    private Uri imgUri;
    private ImageView pictureImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        Button selectBtn = (Button) findViewById(R.id.select_img);
        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sd卡写入要相关权限  所以获取关联文件
                File img_save = new File(getExternalCacheDir(),"output_img.jpg");
                if(img_save.exists()){
                    img_save.delete();
                }
                try {
                    img_save.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(Build.VERSION.SDK_INT > 24){
                    //FileProvider.getUriForFile获取封装好的uri
                    imgUri = FileProvider.getUriForFile(CameraActivity.this,"",img_save);
                }else {
                    imgUri = Uri.fromFile(img_save);
                }

                Intent intent = new Intent("android.media.action.IMAGE_CAPYURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imgUri);
                startActivityForResult(intent,1);
            }
        });

        Button choiceButton = (Button) findViewById(R.id.choice_img);
        choiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(CameraActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

                }else {
                    openAlbum();
                }
            }

        });
    }
    private void openAlbum() {
        Intent _intent = new Intent("android.intent.action.GET_CONTENT");
        _intent.setType("image/*");
        startActivityForResult(_intent,2);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else {
                    Toast.makeText(CameraActivity.this,"You denied the permission",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    try {
                        Bitmap imgBit = BitmapFactory.decodeStream(getContentResolver().openInputStream(imgUri));
                        pictureImg.setImageBitmap(imgBit);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 2:

                break;
        }
    }
}
