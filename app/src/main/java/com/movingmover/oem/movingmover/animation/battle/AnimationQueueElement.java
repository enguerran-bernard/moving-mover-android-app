package com.movingmover.oem.movingmover.animation.battle;

import android.content.Context;
import android.widget.ImageView;

public abstract class AnimationQueueElement {
    public static enum DIRECTION {
        RIGHT,
        LEFT,
        UP,
        DOWN,
    }

    protected Context mContext;
    protected BoardAnimationManager mBoardAnimationManager;
    protected ImageView mImageView;
    protected int mDuration;

    public AnimationQueueElement(Context context, BoardAnimationManager boardAnimation, ImageView imageView,
                                 int duration) {
        mContext = context;
        mBoardAnimationManager = boardAnimation;
        mImageView = imageView;
        mDuration = duration;
    }

    public abstract void animate();
    public abstract int getSoundId();
}
