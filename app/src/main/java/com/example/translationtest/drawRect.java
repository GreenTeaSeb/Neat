package com.example.translationtest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.PixelCopy;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class drawRect extends View {
    Paint paint = new Paint();
    float top;
    float left;
    float bottom;
    float right;
    SurfaceView surfaceView;
    Surface surface;
    Bitmap bitmap;
    RectF rect;

    public drawRect(Context ctx){
        super(ctx);
    }

    public drawRect(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        TypedArray attr = ctx.getTheme().obtainStyledAttributes(attrs,R.styleable.drawRect,0,0);
        try{
            top = attr.getFloat(R.styleable.drawRect_top,0);
            left = attr.getFloat(R.styleable.drawRect_left,0);
            bottom = attr.getFloat(R.styleable.drawRect_bottom,0);
            right = attr.getFloat(R.styleable.drawRect_right,0);
        }finally {
            attr.recycle();
        }
        invalidate();
        requestLayout();
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if( getVisibility() == VISIBLE ) {
            int startX = 0;
            int startY = 0;
            int endX;
            int endY;

            switch (e.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    left = (int) e.getX();
                    top = (int) e.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    right = (int) e.getX();
                    bottom = (int) e.getY();
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:

                default:
                    return super.onTouchEvent(e);
            }
        }
        return true;
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if(visibility == VISIBLE){

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
       // paint.setPathEffect(new DashPathEffect(new float[]{20,30},0));
        rect = new RectF();
        rect.set(left,top,right,bottom);
        canvas.drawRoundRect(rect,10,10,paint);
    }
    public void clear(){
        left = 0;
        right = 0;
        bottom = 0;
        top = 0;
        invalidate();
    }
    public void surfaceToBitmap(){
        //surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        //surface = surfaceView.getHolder().getSurface();
        //bitmap = Bitmap.createBitmap(surfaceView.getWidth(),surfaceView.getHeight(),Bitmap.Config.ARGB_8888);
        //HandlerThread pixelHandler = new HandlerThread("PixelCopy");
        //pixelHandler.start();
        //PixelCopy.request(surface, rect, bitmap, PixelCopy.OnPixelCopyFinishedListener, new Handler());

    }
}

