package com.movingmover.oem.movingmover.sound;

import android.content.Context;
import android.media.SoundPool;
import android.util.Log;

import com.movingmover.oem.movingmover.R;

import java.util.HashMap;

public class SoundHelper {
    private static final int SOUND_PRIORITY = 1;
    private static final int NO_LOOP = 0;
    private static final float LEFT_VOLUME = 1.0f;
    private static final float RIGHT_VOLUME = 1.0f;
    private static final float SOUND_RATE = 1.0f;

    private static HashMap<Integer, Integer> sLoadedSounds = new HashMap<>();
    private static int sLoadedSoundsNumber = 0;
    private static SoundPool sSoundPool = new SoundPool.Builder().build();

    public static void loadSounds(Context context, final SoundReadyListener listener) {
        loadSound(context, sSoundPool, R.raw.putplayer);
        loadSound(context, sSoundPool, R.raw.move);
        loadSound(context, sSoundPool, R.raw.putarrow);
        loadSound(context, sSoundPool, R.raw.hitarrow);
        loadSound(context, sSoundPool, R.raw.death);

        if (sLoadedSoundsNumber == sLoadedSounds.size()) {
            listener.onSoundsLoaded();
        } else {
            sSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int id, int status) {
                    synchronized (SoundHelper.class) {
                        sLoadedSoundsNumber++;
                        if (sLoadedSoundsNumber == sLoadedSounds.size()) {
                            listener.onSoundsLoaded();
                        }
                    }
                }
            });
        }
    }

    private static void loadSound(Context context, SoundPool soundPool, int resId) {
        if (sLoadedSounds.get(resId) != null) {
            return;
        }
        int res = soundPool.load(context, resId, SOUND_PRIORITY);
        Log.i("soundHelper", "play sound... add: " + resId + " - " + res);
        sLoadedSounds.put(resId, res);
    }

    public static void playSound(int resId) {
        Log.i("soundHelper", "play sound... get begins... " + R.raw.death + " vs " + resId);
        if (sLoadedSounds.get(resId) == null) {
            return;
        }
        Log.i("soundHelper", "play sound... get: " + sLoadedSounds.get(resId));
        sSoundPool.play(sLoadedSounds.get(resId), LEFT_VOLUME, RIGHT_VOLUME, SOUND_PRIORITY, NO_LOOP, SOUND_RATE);
    }
}
