package com.movingmover.oem.movingmover.animation;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.movingmover.oem.movingmover.helper.Coord;
import com.movingmover.oem.movingmover.widget.BoardView;

public class PlayerMove extends AnimationQueueElement {
        private Coord mCoordInit;
        private Coord mCoordDest;
        private boolean mIsChoosed;

        public PlayerMove(BoardAnimationManager boardAnimationManager, ImageView imageView,
                          int duration, Coord coordInit, Coord coordDest, boolean isChoosed) {
            super(boardAnimationManager, imageView, duration);
            mCoordInit = coordInit;
            mCoordDest = coordDest;
            mIsChoosed = isChoosed;
        }

    @Override
    public void animate() {
        final BoardView boardView = mBoardAnimationManager.getBoardView();

        final int size = boardView.getSize();
        int width = boardView.getWidth();
        mImageView.bringToFront();
        final Drawable imageDrawable = mImageView.getDrawable().getConstantState().newDrawable().mutate();
        int deltaX = (mCoordDest.x - mCoordInit.x) * width / boardView.getSize();
        int deltaY = (mCoordDest.y - mCoordInit.y) * width / boardView.getSize();

        TranslateAnimation animation = new TranslateAnimation(
                0,
                deltaX,
                0,
                deltaY);
        animation.setDuration(mDuration);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mImageView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (!mIsChoosed) {
                            int blackColor = Color.parseColor("#FF0000");
                            PorterDuff.Mode mMode = PorterDuff.Mode.SRC_ATOP;
                            Drawable drawable = mImageView.getDrawable();
                            drawable.setColorFilter(blackColor, mMode);
                        }
                    }
                });
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                boardView.clearAnimation();
                mImageView.post(new Runnable() {
                    @Override
                    public void run() {
                        boardView.setImagePosition(mImageView, mCoordDest);
                        if (!mIsChoosed) {
                            mImageView.setImageDrawable(imageDrawable);
                        }
                        mBoardAnimationManager.onAnimationEnd();
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation.setFillAfter(false);
        mImageView.startAnimation(animation);
    }
}
