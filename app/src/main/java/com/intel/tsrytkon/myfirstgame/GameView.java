package com.intel.tsrytkon.myfirstgame;

import android.graphics.Canvas;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by tsrytkon on 10/31/15.
 */
public interface GameView {

    public void draw();
    public View getView();
    public SoundPoolPlayer getSounds();
    public ArrayList<GameShot> getShots();
    public GameStats getStats();
}
