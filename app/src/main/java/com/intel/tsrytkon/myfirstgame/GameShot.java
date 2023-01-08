package com.intel.tsrytkon.myfirstgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class GameShot extends GameObj {
    private int dir_x = 0;
    private int dir_y = 0;
    private Rect m_bounds = null;
    private int scolor = Color.YELLOW;
    public GameShot(int x, int y, GameView gv) {
        super(x, y, 10, 10, gv);
        // default dir up
        set_dir(0, -15);
    }
    public GameShot(int x, int y, int dir_x, int dir_y, int color, GameView gv) {
        super(x, y, 10, 10, gv);
        // default dir up
        set_dir(dir_x, dir_y);
        scolor = color;
    }
    public void set_dir(int vx, int vy) {
        dir_x = vx;
        dir_y = vy;
    }
    public void set_bounds(Rect bounds) {
        m_bounds = bounds;
    }
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Paint shotPaint = new Paint();
        shotPaint.setColor(scolor);
        canvas.drawCircle(m_x, m_y, 5, shotPaint);
    }
    public boolean move() {
        if (m_y > 0 && m_y < gameView.getView().getHeight()) {
            m_y += dir_y;
            m_x += dir_x;
            return true;
        }
        else {
            return false;
        }
    }
}
