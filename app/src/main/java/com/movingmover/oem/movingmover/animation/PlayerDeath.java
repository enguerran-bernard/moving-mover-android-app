package com.movingmover.oem.movingmover.animation;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.movingmover.oem.movingmover.widget.BoardView;

public class PlayerDeath extends AnimationQueueElement {

    public PlayerDeath(BoardAnimationManager boardAnimationManager, ImageView imageView,
                       int duration) {
        super(boardAnimationManager, imageView, duration);
    }

    @Override
    public void animate() {
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
                        //boardView.getPlayers().remove(null, mImageView);
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
