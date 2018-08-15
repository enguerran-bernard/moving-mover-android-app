package com.movingmover.oem.movingmover.animation;

import android.graphics.Color;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.movingmover.oem.movingmover.widget.ArrowView;
import com.movingmover.oem.movingmover.widget.BoardView;

public class HitArrow extends AnimationQueueElement {

    private static final String DESTROYED = "DESTROYED";
    private static final String DAMAGED = "DAMAGED";

    private String mState;

    public HitArrow(BoardAnimationManager boardAnimationManager, ArrowView imageView,
                    int duration, String state) {
        super(boardAnimationManager, imageView, duration);
        mState = state;
    }

    @Override
    public void animate() {
        switch(mState) {
            case DESTROYED:
                destroyArrow();
                break;
            case DAMAGED:
                damageArrow();
                break;
        }
    }

    private void destroyArrow() {
        final BoardView boardView = mBoardAnimationManager.getBoardView();
        AlphaAnimation animation = new AlphaAnimation(1.0f, 0.0f);
        animation.setDuration(mDuration);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                boardView.clearAnimation();
                mImageView.post(new Runnable() {
                    @Override
                    public void run() {
                        mBoardAnimationManager.onAnimationEnd();
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation.setFillAfter(true);
        mImageView.startAnimation(animation);
    }

    private void damageArrow() {
        final BoardView boardView = mBoardAnimationManager.getBoardView();
        AlphaAnimation animation = new AlphaAnimation(1.0f, 0.9f);
        animation.setDuration(mDuration);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mImageView.post(new Runnable() {
                    @Override
                    public void run() {
                        ((ArrowView) mImageView).setColorTwo(Color.RED);
                    }
                });
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                boardView.clearAnimation();
                mImageView.post(new Runnable() {
                    @Override
                    public void run() {
                        mBoardAnimationManager.onAnimationEnd();
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation.setFillAfter(true);
        mImageView.startAnimation(animation);
    }
}
