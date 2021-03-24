package com.example.translationtest;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.PixelCopy;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;

import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.test.runner.screenshot.Screen

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class BubbleService extends Service {
    static String INTENTDATA = "com.greentea.Kakoo.screenshotService.INTENTDATA";
    private WindowManager windowMan;
    private LinearLayout ll;
    private View view;
    private LayoutInflater inf;
    private int flag;
    private Paint paint;
    SurfaceView surfaceView;
    Surface surface;
    Button bubble;
    ConstraintLayout toolbar;
    drawRect drawrect;
    ViewGroup.LayoutParams bubbleLayoutParams;
    WindowManager.LayoutParams params;
    WindowManager.LayoutParams paramsOpened;

    DisplayMetrics displayMetrics = new DisplayMetrics();
    MediaProjectionManager mpManager;
    Intent screenshotService;
    Intent intentData;
    Bitmap bitmap;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        params = new WindowManager.LayoutParams(
                WRAP_CONTENT,
                WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |WindowManager.LayoutParams.FLAG_DIM_BEHIND | WindowManager.LayoutParams.FLAG_FULLSCREEN,
                PixelFormat.TRANSLUCENT);

        paramsOpened = new WindowManager.LayoutParams(
                MATCH_PARENT,
                MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM|WindowManager.LayoutParams.FLAG_DIM_BEHIND| WindowManager.LayoutParams.FLAG_FULLSCREEN,
                PixelFormat.TRANSLUCENT);


        params.y = 0;paramsOpened.x = 0;
        params.dimAmount = 0;paramsOpened.dimAmount = 0.5f;
        params.gravity = Gravity.TOP | Gravity.LEFT;paramsOpened.gravity = Gravity.TOP | Gravity.LEFT;


        windowMan =(WindowManager) getSystemService(Context.WINDOW_SERVICE);
        inf = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        view = inf.inflate(R.layout.floating_layout,null);
        // BUTTONS/OBJECTS
         bubble = (Button) view.findViewById(R.id.bubble);
         toolbar = (ConstraintLayout) view.findViewById(R.id.expandedView);
         bubble.getBackground().setAlpha(127);
         drawrect = (drawRect) view.findViewById(R.id.drawRect);
         bubbleLayoutParams = bubble.getLayoutParams();
        surfaceView = (SurfaceView) view.findViewById(R.id.surfaceView);
        surface = surfaceView.getHolder().getSurface();
        screenshotService = new Intent(this,projection.class);
        mpManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        windowMan.getDefaultDisplay().getMetrics(displayMetrics);
        windowMan.addView(view,params);
        //startProjection();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        intentData = intent.getParcelableExtra(INTENTDATA);
        startService(screenshotService);
        bubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                params.dimAmount = 0;
                if(toolbar.getVisibility() != View.GONE) {

                    bubble.setScaleX(0.3F);
                    bubble.setScaleY(0.3F);
                    bubble.getBackground().setAlpha(127);
                    drawrect.clear();
                    toolbar.setVisibility(View.GONE);
                    windowMan.updateViewLayout(view,params);
                } else {
//                    stopService(screenshotService);
                    toolbar.setVisibility(View.VISIBLE);
                    bubble.getBackground().setAlpha(255);
                    bubble.setScaleX(0.5F);
                    bubble.setScaleY(0.5F);
                    surfaceView.setVisibility(View.VISIBLE);
                    drawrect.setVisibility(View.VISIBLE);
                    bubble.setVisibility(View.VISIBLE);
                    windowMan.updateViewLayout(view,paramsOpened);
                }

            }
        });


        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(windowMan != null) {
            windowMan.removeView(view);
        }
    }

    public void startProjection(){
        screenshotService.putExtra(projection.RESULT_INTENT,intentData);
        screenshotService.putExtra(projection.SURFACE,surface);
        screenshotService.putExtra("resultCode",-1);
        screenshotService.putExtra("dpi",displayMetrics.densityDpi);
        screenshotService.putExtra("width",displayMetrics.widthPixels);
        screenshotService.putExtra("height",displayMetrics.heightPixels);
    }

    public void screencap(){
        ScreenCapture screenCapture;
    }

}
