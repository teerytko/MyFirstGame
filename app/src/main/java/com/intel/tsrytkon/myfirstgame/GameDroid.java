package com.intel.tsrytkon.myfirstgame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;

import java.util.Random;

public class GameDroid extends GameObj {
    private int tickCount;
    private Random rand = new Random();
    protected Bitmap bmp = null;
    public GameDroid(int x, int y, GameView gv) {
        super(x, y, gv);
        bmp = BitmapFactory.decodeResource(gv.getView().getResources(),
                R.drawable.ic_launcher);
        // override size params
        m_w = bmp.getWidth();
        m_h = bmp.getHeight();

    }
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(bmp, m_x, m_y, null);
    }
    public void shoot() {
        if (rand.nextFloat() < 0.005) {
            // shoot it
            gameView.getShots().add(
                    new GameShot(m_x+(m_w/2), m_y+m_h, 0, 10, Color.RED, gameView));
            gameView.getSounds().playShortResource(R.raw.blaster_solo);
        }
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
