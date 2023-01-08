package com.intel.tsrytkon.myfirstgame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.Arrays;
import java.util.Collections;

public class GameSpaceship extends GameObj {
    protected Bitmap bmp = null;
    protected int center_x = 0;
    int LOAD_TIME = 100;
    Integer[] avail_shots = {LOAD_TIME, LOAD_TIME};
    int energy = 100;
    public GameSpaceship(int x, int y, GameView gv) {
        super(x, y, gv);
        bmp = BitmapFactory.decodeResource(gv.getView().getResources(),
                R.drawable.spaceship);
        // override size params
        m_w = bmp.getWidth();
        m_h = bmp.getHeight();
        center_x = m_w / 2;
    }
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(bmp, m_x, m_y, null);
    }
    public void move(int target_x) {
        //  convert the target_x to point to center of ship
        target_x -= center_x;
        m_x += (target_x-m_x)/5;

    }
    public boolean shoot() {
        // if we have loaded shot
        if (avail_shots[0] == LOAD_TIME) {
            // shoot it
            avail_shots[0] = 0;
            gameView.getShots().add(new GameShot(m_x, m_y, gameView));
            gameView.getSounds().playShortResource(R.raw.blaster_solo);
            return true;
        } else {
            gameView.getSounds().playShortResource(R.raw.blaster_fail);
            return false;
        }
    }
    public void load_cycle() {
        int s = 0;
        Arrays.sort(avail_shots, Collections.reverseOrder());
        for (int i=0; i < avail_shots.length; i++) {
            if (avail_shots[i] < LOAD_TIME)
                avail_shots[i]++;
        }
        s += avail_shots[0];
        GameStats ship_stats = gameView.getStats();;
        ship_stats.set_shots(s);
        ship_stats.set_energy(energy);
    }

}
