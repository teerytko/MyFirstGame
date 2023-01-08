package com.intel.tsrytkon.myfirstgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class GameStats extends GameObj {
    private int energy = 100;
    private int shots  = 100;
    public GameStats(int x, int y, GameView gv) {
        super(x, y, x+40, y+100, gv);
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