package com.intel.tsrytkon.myfirstgame;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by tsrytkon on 10/25/15.
 */
public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback, GameView {
    private Bitmap droid;
    private Bitmap spaceship;
    private Bitmap background;
    private MainThread m_thread = null;
    private SurfaceHolder m_holder;
    private static final String TAG = MainThread.class.getSimpleName();
    public int droid_x, droid_y = 0;
    public int ship_x, ship_y = 0;
    public int touch_x, touch_y_start = 0;
    public int shot_x = -1;
    public int shot_y = 0;

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
        surfaceHolder.addCallback(this);
        m_thread = new MainThread(this);
    }

    public int droid_width() {
        return droid.getWidth();
    }

    public int droid_height() {
        return droid.getWidth();
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        System.out.println("surfaceCreated");
        m_holder = holder;
        // Set the droid initial position
        // Load and scale the graphics
        droid = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_launcher);
        spaceship = BitmapFactory.decodeResource(getResources(),
                R.drawable.spaceship);
        Bitmap tmpBmp = BitmapFactory.decodeResource(getResources(), R.drawable.space_1);
        float scale = (float)tmpBmp.getHeight()/(float)getHeight();
        int newWidth = Math.round(tmpBmp.getWidth()/scale);
        int newHeight = Math.round(tmpBmp.getHeight()/scale);
        background = Bitmap.createScaledBitmap(tmpBmp, newWidth, newHeight, true);
        // starting positions
        droid_x = getWidth() / 2 - droid.getWidth();
        droid_y = getHeight() / 2;
        ship_x = getWidth() / 2;
        ship_y = getHeight() - getHeight() / 12 - spaceship.getWidth();
        touch_x = getWidth() / 2 - spaceship.getWidth();
        m_thread.setRunning(true);
        m_thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        System.out.println("surfaceChanged");

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        System.out.println("surfaceDestroyed");
        m_thread.setRunning(false);
        try {
            m_thread.join();
        } catch (InterruptedException e) {
            System.out.println("Could not close MainThread!");
            System.out.println(e);
        }


    }

    public void draw() {
        Canvas canvas = m_holder.lockCanvas(null);
        if (canvas != null) {
            canvas.drawColor(Color.BLACK);
            canvas.drawBitmap(background, 0, 0, null);
            canvas.drawBitmap(droid, droid_x, droid_y, null);
            if (shot_y > 0) {
                Paint shotpaint = new Paint();
                shotpaint.setColor(Color.YELLOW);
                canvas.drawCircle(shot_x, shot_y, 5, shotpaint);
            }

                canvas.drawBitmap(spaceship, ship_x-spaceship.getWidth()/2, ship_y, null);
            m_holder.unlockCanvasAndPost(canvas);
        }

    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()== MotionEvent.ACTION_DOWN) {
            touch_y_start = (int)event.getY();
        }
        else if (event.getAction()== MotionEvent.ACTION_UP) {
            int touch_y_end = (int)event.getY();
            System.out.println("Y diff "+ (touch_y_end-touch_y_start));
            if ((touch_y_end-touch_y_start) > 60) {
                System.out.println("SHOOT!!!");
                shot_x = ship_x;
                shot_y = ship_y;
            }
        }
        touch_x = (int)event.getX();
        return true; //return super.onTouchEvent(event);
    }

}

