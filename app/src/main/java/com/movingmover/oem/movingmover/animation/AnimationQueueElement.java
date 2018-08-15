package com.movingmover.oem.movingmover.animation;

import android.widget.ImageView;

public abstract class AnimationQueueElement {
    public static enum DIRECTION {
        RIGHT,
        LEFT,
        UP,
        DOWN,
    }

    protected BoardAnimationManager mBoardAnimationManager;
    protected ImageView mImageView;
    protected int mDuration;

    public AnimationQueueElement(BoardAnimationManager boardAnimation, ImageView imageView,
                                 int duration) {
        mBoardAnimationManager = boardAnimation;
        mImageView = imageView;
        mDuration = duration;
    }

    public abstract void animate();
}
