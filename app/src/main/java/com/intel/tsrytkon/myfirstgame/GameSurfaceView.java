package com.intel.tsrytkon.myfirstgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by tsrytkon on 10/25/15.
 */
public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private Bitmap bmpIcon;

    public GameSurfaceView(Context context) {
        super(context);
        init();
    }

    public GameSurfaceView(Context context,
                         AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GameSurfaceView(Context context,
                         AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void init() {
        System.out.println("Initializing Game!");
        SurfaceHolder surfaceHolder = getHolder();
        bmpIcon = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_launcher);
        surfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        System.out.println("surfaceCreated");
        Canvas canvas = holder.lockCanvas(null);
        drawSomething(canvas);
        holder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        System.out.println("surfaceChanged");

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        System.out.println("surfaceDestroyed");

    }

    private void drawSomething(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(bmpIcon,
                getWidth()/2, getHeight()/2, null);
    }

}

