package com.intel.tsrytkon.myfirstgame;

import static java.util.Arrays.sort;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Created by tsrytkon on 10/25/15.
 */
public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback, GameView {
    public SoundPoolPlayer sounds;
    protected MediaPlayer player;
    protected Bitmap background;
    protected MainThread m_thread = null;
    protected SurfaceHolder m_holder;
    protected static final String TAG = MainThread.class.getSimpleName();
    public GameSpaceship ship = null;
    public int touch_x, touch_y_start = 0;
    public ArrayList<GameShot> shots = new ArrayList();
    public ArrayList<GameDroid> droids  = new ArrayList();
    public GameStats stats = null;
    boolean firstTouch = false;
    long time;

    public View getView() {
        return this;
    }
    public SoundPoolPlayer getSounds() {
        return this.sounds;
    }
    public ArrayList<GameShot> getShots() {
        return shots;
    }
    public GameStats getStats() {
        return stats;
    }

    public GameSurfaceView(Context context) {
        super(context);
        init(context);
    }

    public GameSurfaceView(Context context,
                         AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GameSurfaceView(Context context,
                         AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void init(Context context) {
        System.out.println("Initializing Game!");
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        m_thread = new MainThread(this);
        sounds = new SoundPoolPlayer(context);
        player = MediaPlayer.create(context, R.raw.test_reverb);
        player.setLooping(true); // Set looping
        player.setVolume(100,100);
    }

    public void initialize_game() {
        GameLevels.initialize_game_level1(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // Initialize game objects
        System.out.println("surfaceCreated");
        m_holder = holder;
        // Load and scale the graphics
        Bitmap tmpBmp = BitmapFactory.decodeResource(getResources(), R.drawable.space_1);
        float scale = (float)tmpBmp.getHeight()/(float)getHeight();
        int newWidth = Math.round(tmpBmp.getWidth()/scale);
        int newHeight = Math.round(tmpBmp.getHeight()/scale);
        background = Bitmap.createScaledBitmap(tmpBmp, newWidth, newHeight, true);

        initialize_game();
        player.start();
        touch_x = getWidth() / 2;
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
        player.stop();
        player.release();
        m_thread.setRunning(false);
        sounds.release();
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
            stats.draw(canvas);
            for (int j=0; j<droids.size(); j++) {
                droids.get(j).draw(canvas);
            }
            for (int i=0; i<shots.size(); i++) {
                shots.get(i).draw(canvas);
            }
            ship.draw(canvas);
            m_holder.unlockCanvasAndPost(canvas);
        }
    }
    public void drawFinish(boolean win) {
        final float testTextSize = 58f;
        Canvas canvas = m_holder.lockCanvas(null);
        if (canvas != null) {
            canvas.drawColor(Color.BLACK);
            canvas.drawBitmap(background, 0, 0, null);
            Paint textPaint = new Paint();
            textPaint.setColor(Color.YELLOW);
            textPaint.setTextSize(testTextSize);
            if (win)
                canvas.drawText("You won!", getWidth()/2, getHeight()/2, textPaint);
            else
                canvas.drawText("Game over!", getWidth()/2, getHeight()/2, textPaint);
            ship.draw(canvas);
            m_holder.unlockCanvasAndPost(canvas);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        touch_y_start = (int)event.getY();
        if (touch_y_start > ship.m_y-10) {
            if (event.getAction()== MotionEvent.ACTION_DOWN) {
                if(firstTouch && (System.currentTimeMillis() - time) <= 300) {
                    ship.shoot();
                    firstTouch = false;
                } else {
                    firstTouch = true;
                    time = System.currentTimeMillis();
                    //return super.onTouchEvent(event);
                    //return false;Use this if you dont want to call default onTouchEvent()
                }
            }
            else if (event.getAction()== MotionEvent.ACTION_UP) {
                int touch_y_end = (int)event.getY();
                System.out.println("Y diff "+ (touch_y_end-touch_y_start));
            }
            touch_x = (int)event.getX();
        }
        return true;
    }

}

