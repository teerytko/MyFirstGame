package com.intel.tsrytkon.myfirstgame;

import com.intel.tsrytkon.myfirstgame.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.MenuItem;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class MainGameActivity extends Activity {

    protected SurfaceView mMainView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE;
        decorView.setSystemUiVisibility(uiOptions);
        //mMainView = (SurfaceView) findViewById(R.id.game_surface);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

}
