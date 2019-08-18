package com.movingmover.oem.movingmover.animation.battle;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.movingmover.oem.movingmover.R;
import com.movingmover.oem.movingmover.helper.Coord;
import com.movingmover.oem.movingmover.widget.BoardView;

public class PutArrow extends AnimationQueueElement {

    private Coord mCoordInit;
    private Coord mCoordDest;

    public PutArrow(Context context, BoardAnimationManager boardAnimationManager, ImageView imageView,
                    int duration, Coord coordInit, Coord coordDest) {
        super(context, boardAnimationManager, imageView, duration);
        mCoordInit = coordInit;
        mCoordDest = coordDest;
    }

    @Override
    public void animate() {
        final BoardView boardView = mBoardAnimationManager.getBoardView();

        int size = boardView.getSize();
        int width = boardView.getWidth();
        boardView.setImagePosition(mImageView, mCoordInit);
        mImageView.setVisibility(View.VISIBLE);
        mImageView.bringToFront();
        int deltaX = (mCoordDest.x - mCoordInit.x) * width / boardView.getSize();
        int deltaY = (mCoordDest.y - mCoordInit.y) * width / boardView.getSize();

        if (mCoordDest.x >= 0 && mCoordDest.y >= 0 && mCoordDest.x < size && mCoordDest.y < size) {
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
                            mBoardAnimationManager.onAnimationStart();
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

    @Override
    public int getSoundId() {
        return R.raw.putarrow;
    }
}
