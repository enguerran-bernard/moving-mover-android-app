package com.movingmover.oem.movingmover.animation;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.movingmover.oem.movingmover.helper.Coord;
import com.movingmover.oem.movingmover.widget.BoardView;

public class PutPlayer extends AnimationQueueElement {
    private Coord mCoord;

    public PutPlayer(BoardAnimationManager boardAnimationManager, ImageView imageView,
                     int duration, Coord coord) {
        super(boardAnimationManager, imageView, duration);
        mCoord = coord;
    }

    @Override
    public void animate() {
        mImageView.setVisibility(View.VISIBLE);
        final BoardView boardView = mBoardAnimationManager.getBoardView();
        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(mDuration);
        boardView.setImagePosition(mImageView, mCoord);
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
}