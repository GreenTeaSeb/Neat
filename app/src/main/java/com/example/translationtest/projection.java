package com.example.translationtest;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.io.File;

import static android.hardware.display.DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR;
import static android.hardware.display.DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC;

public class projection extends Service {
    final int MEDIA_REQUEST_CODE = 1;
    static final String RESULT_INTENT = "com.greentea.Kakoo.screenshotService.RESULT_INTENT";
    static final String SURFACE = "com.greentea.Kakoo.screenshotService.SURFACE";
    static final String SURFACEVIEW = "com.greentea.Kakoo.screenshotService.SURFACEVIEW";

    int dpi;
    int width;
    int height;
    int resultCode;
    Intent resultIntent;
    DisplayMetrics displayMetrics = new DisplayMetrics();
    File outfile = null;
    MediaProjectionManager mpManager;
    MediaProjection projection;
    Parcelable resultIntentData;
    Notification notification;
    Surface surface;

    boolean showedNotification = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        mpManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        notification = new Notification.Builder(this,"projectionChannelID").build();
        startForeground(101,notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        resultIntent = intent.getParcelableExtra(RESULT_INTENT);
        surface = intent.getParcelableExtra(SURFACE);
        resultCode = intent.getIntExtra("resultCode",0);
        dpi = intent.getIntExtra("dpi",0);
        width = intent.getIntExtra("width",0);
        height = intent.getIntExtra("height",0);

        if (resultCode == -1){

            getProjection();
            projection.createVirtualDisplay("display", width, height, dpi, VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR, surface, null, null);

        }
        return START_STICKY;
    }

    public void getProjection() {
        projection = mpManager.getMediaProjection(resultCode,resultIntent);
    }

    private void createNotificationChannel(){
        CharSequence name = getString(R.string.CHANNEL_NAME);
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel notificationChannel = new NotificationChannel("projectionChannelID",name,importance);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(notificationChannel);
    }

    @Override
    public void onDestroy() {
        projection.stop();
    }
}
