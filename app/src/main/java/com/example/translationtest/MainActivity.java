 package com.example.translationtest;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import static android.Manifest.permission.SYSTEM_ALERT_WINDOW;


 public class MainActivity extends AppCompatActivity {
     final int MEDIA_REQUEST_CODE = 1;
     MediaProjectionManager mpManager;
     int resultcode;
     Intent bubbleInt;




     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bubbleInt = new Intent(MainActivity.this,BubbleService.class);
        Switch s = (Switch) findViewById(R.id.widget);
        Context ctx = getApplicationContext();
        //mpManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);

        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if(isChecked == true){
                        if(resultcode != -1) {
                            startActivityForResult(mpManager.createScreenCaptureIntent(), MEDIA_REQUEST_CODE);
                        }else if(resultcode == -1){
                            startService(bubbleInt);
                        }
                    }
                    else{
                        stopService(bubbleInt);
                    }

            }
        });
    }

     @Override
     protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
         switch (requestCode){
             case MEDIA_REQUEST_CODE:
                 resultcode = resultCode;
                 bubbleInt.putExtra(BubbleService.INTENTDATA,data);
                 startService(bubbleInt);
         }
     }
}