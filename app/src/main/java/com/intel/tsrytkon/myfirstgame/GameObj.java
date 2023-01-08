package com.intel.tsrytkon.myfirstgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class GameObj {
    public int m_x;
    public int m_y;
    public int m_w;
    public int m_h;
    protected static boolean DEBUGSIZE = false;
    protected GameView gameView = null;
    public GameObj(int x, int y, int w, int h, GameView gv) {
        m_x = x;
        m_y = y;
        m_w = w;
        m_h = h;
        gameView = gv;
    }
    public GameObj(int x, int y, GameView gv) {
        m_x = x;
        m_y = y;
        gameView = gv;
    }
    public void draw(Canvas canvas) {
        if (DEBUGSIZE) {
            Paint p = new Paint(Color.YELLOW);
            canvas.drawRect(new Rect(m_x, m_y, m_x+m_w, m_y+m_h), p);
        }
    }

    public boolean hits(GameObj obj) {
        if ( m_x < obj.m_x && obj.m_x < m_x+m_w &&
                m_y < obj.m_y && obj.m_y < m_y+m_h ) {
            System.out.println("Hit!!!");
            return true;
        }
        return false;
    }

}
