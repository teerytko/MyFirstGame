package com.intel.tsrytkon.myfirstgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class GameBase {
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
        private int dir_x = 0;
        private int dir_y = 0;
        private int scolor = Color.YELLOW;
        public Shot(int x, int y) {
            super(x, y, 10, 10);
            // default dir up
            set_dir(0, -15);
        }
        public Shot(int x, int y, int dir_x, int dir_y, int color) {
            super(x, y, 10, 10);
            // default dir up
            set_dir(dir_x, dir_y);
            scolor = color;
        }
        public void set_dir(int vx, int vy) {
            dir_x = vx;
            dir_y = vy;
        }
        public void draw(Canvas canvas) {
            Paint shotPaint = new Paint();
            shotPaint.setColor(scolor);
            canvas.drawCircle(m_x, m_y, 5, shotPaint);
        }
        public boolean move(Canvas canvas) {
            if (m_y > 0 && m_y < canvas.getHeight()) {
                m_y += dir_y;
                m_x += dir_x;
                return true;
            }
            else {
                return false;
            }
        }
    }


}
