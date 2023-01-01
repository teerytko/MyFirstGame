package com.intel.tsrytkon.myfirstgame;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

/**
 * Created by tsrytkon on 11/1/15.
 */
public class SoundPoolPlayer {

    private SoundPool mShortPlayer= null;
    private HashMap mSounds = new HashMap();

    public SoundPoolPlayer(Context pContext)
    {
        // setup Soundpool
        this.mShortPlayer = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);

        mSounds.put(R.raw.blaster_solo, this.mShortPlayer.load(pContext, R.raw.blaster_solo, 1));
        mSounds.put(R.raw.blaster_fail, this.mShortPlayer.load(pContext, R.raw.blaster_fail, 1));
        mSounds.put(R.raw.slave_seismic, this.mShortPlayer.load(pContext, R.raw.slave_seismic, 1));
    }

    public void playShortResource(int piResource) {
        int iSoundId = (Integer) mSounds.get(piResource);
        this.mShortPlayer.play(iSoundId, 0.99f, 0.99f, 0, 0, 1);
    }

    // Cleanup
    public void release() {
        // Cleanup
        this.mShortPlayer.release();
        this.mShortPlayer = null;
    }
}
