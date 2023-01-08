package com.intel.tsrytkon.myfirstgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.SurfaceHolder;

import java.util.ArrayList;

public class GameLevels extends GameBase {

    public static void initialize_game_level1(GameSurfaceView surfaceView) {
        surfaceView.stats = new GameStats(50, surfaceView.getHeight()-150, surfaceView);
        // starting positions
        int droid_y = surfaceView.getHeight() / 12;
        int droid_x = surfaceView.getWidth() / 12;
        // Set the droid initial position
        for (int i=0; i<10; i++) {
            surfaceView.droids.add(new GameDroid((i+1)*droid_x,
                    (i%2)*droid_y+droid_y, surfaceView));
        }
        int ship_x = surfaceView.getWidth() / 2;
        int ship_y = surfaceView.getHeight() - surfaceView.getHeight() / 10;
        surfaceView.ship = new GameSpaceship(
                ship_x, ship_y, surfaceView);
    }

}
