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
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by tsrytkon on 10/25/15.
 */
public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback, GameView {
    public class Obj {
        public int m_x;
        public int m_y;
        public int m_w;
        public int m_h;
        public Obj(int x, int y, int w, int h) {
            m_x = x;
            m_y = y;
            m_w = w;
            m_h = h;
        }
        public boolean hits(Obj obj) {
            if ( m_x < obj.m_x && obj.m_x < m_x+m_w &&
                 m_y < obj.m_y && obj.m_y < m_y+m_h ) {
                System.out.println("Hit!!!");
                return true;
            }
            return false;
        }
    }
    public class Stats extends Obj {
        private int energy = 100;
        private int shots  = 100;
        public Stats(int x, int y) {
            super(x, y, x+40, y+100);
        }
        public void set_shots(int s) { shots = s; }
        public void set_energy(int e) { energy = e; }
        public void draw(Canvas canvas) {
            Paint shotPaint = new Paint();
            shotPaint.setColor(Color.GREEN);
            canvas.drawRect(m_x, m_y, m_x+10, m_y+energy, shotPaint);
            shotPaint.setColor(Color.YELLOW);
            canvas.drawRect(m_x+30, m_y, m_x+40, m_y+shots, shotPaint);
        }
    }

    public class Shot extends Obj {
        public Shot(int x, int y, int w, int h) {
            super(x, y, w, h);
        }
        public void draw(Canvas canvas) {
            Paint shotPaint = new Paint();
            shotPaint.setColor(Color.YELLOW);
            canvas.drawCircle(m_x, m_y, 5, shotPaint);
        }
    }

    public class Droid extends Obj {
        private int tickCount;
        public Droid(int x, int y, int w, int h) {
            super(x, y, w, h);
        }
        public void draw(Canvas canvas) {
            canvas.drawBitmap(droid, m_x, m_y, null);
        }
        public void move() {
            if (tickCount < 10) {
                m_x += 3;
            }
            else if (tickCount < 20) {
                m_y += 3;
            }
            else if (tickCount < 30) {
                m_x -= 3;
            }
            else if (tickCount < 40) {
                m_y -= 1;
            }
            else
                tickCount = 0;
            tickCount++;
        }
    }

    public class Spaceship extends Obj {
        protected Stats ship_stats = null;
        int LOAD_TIME = 100;
        Integer shots[] = {LOAD_TIME, LOAD_TIME};
        public Spaceship(int x, int y, int w, int h, Stats stats) {
            super(x, y, w, h);
            ship_stats = stats;
        }
        public void draw(Canvas canvas) {
            canvas.drawBitmap(spaceship, m_x - m_w / 2, m_y, null);
        }
        public void move(int target_x) {
            m_x += (target_x-m_x)/5;
        }
        public boolean shoot() {
            // if we have loaded shot
            if (shots[0] == LOAD_TIME) {
                // shoot it
                shots[0] = 0;
                return true;
            }
            return false;
        }
        public void load_cycle() {
            int s = 0;

            Arrays.sort(shots, Collections.reverseOrder());
            for (int i=0; i < shots.length; i++) {
                if (shots[i] < LOAD_TIME)
                    shots[i]++;
            }
            s += shots[0];
            ship_stats.set_shots(s);
        }

    }
    public SoundPoolPlayer sounds;
    private Bitmap droid;
    private Bitmap spaceship;
    private Bitmap background;
    private MainThread m_thread = null;
    private SurfaceHolder m_holder;
    private static final String TAG = MainThread.class.getSimpleName();
    public Spaceship ship = null;
    public int touch_x, touch_y_start = 0;
    public ArrayList<Shot> shots = new ArrayList();
    public ArrayList<Droid> droids  = new ArrayList();
    public Stats stats = null;

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
    }

    public int droid_width() {
        return droid.getWidth();
    }

    public int droid_height() {
        return droid.getWidth();
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // Initialize game objects
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
        stats = new Stats(50, getHeight()-150);

        // starting positions
        int droid_y = getHeight() / 12;
        int droid_x = getWidth() / 12;
        for (int i=0; i<10; i++) {
            droids.add(new Droid((i+1)*droid_x, droid_y, droid.getWidth(),droid.getHeight()));
        }
        int ship_x = getWidth() / 2;
        int ship_y = getHeight() - getHeight() / 12 - spaceship.getWidth();
        ship = new Spaceship(ship_x, ship_y, spaceship.getWidth(), spaceship.getHeight(), stats);
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
    public void drawFinish() {
        final float testTextSize = 58f;
        Canvas canvas = m_holder.lockCanvas(null);
        if (canvas != null) {
            canvas.drawColor(Color.BLACK);
            canvas.drawBitmap(background, 0, 0, null);
            Paint textPaint = new Paint();
            textPaint.setColor(Color.YELLOW);
            textPaint.setTextSize(testTextSize);
            canvas.drawText("You won!", getWidth()/2, getHeight()/2, textPaint);
            ship.draw(canvas);
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
                if ( ship.shoot() ) {
                    shots.add(new Shot(ship.m_x, ship.m_y, spaceship.getWidth(), spaceship.getHeight()));
                    sounds.playShortResource(R.raw.blaster_solo);
                }
                else {
                    sounds.playShortResource(R.raw.blaster_fail);
                }
            }
        }
        touch_x = (int)event.getX();
        return true;
    }

}

