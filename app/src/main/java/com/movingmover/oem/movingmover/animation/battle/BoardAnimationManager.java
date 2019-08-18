package com.movingmover.oem.movingmover.animation.battle;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.movingmover.oem.movingmover.helper.Coord;
import com.movingmover.oem.movingmover.sound.SoundHelper;
import com.movingmover.oem.movingmover.widget.ArrowView;
import com.movingmover.oem.movingmover.widget.BoardView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class BoardAnimationManager implements AnimationQueueElementListener {

    private static final int  ANIMATION_DURATION = 1000;

    private ArrayList<AnimationQueueElement> mAnimationQueue;

    private Context mContext;
    private BoardView mBoardView;
    private AnimationHandler mAnimationHandler;

    public BoardAnimationManager(Context context, BoardView boardView) {
        mContext = context;
        mBoardView = boardView;
        mAnimationQueue = new ArrayList<>();
        mAnimationHandler = new AnimationHandler(this);
    }

    public BoardView getBoardView() {
        return mBoardView;
    }

    public void stopAnimations() {
        mAnimationHandler.removeCallbacksAndMessages(null);
    }

    public void putPlayer(ImageView imageView, Coord coord) {
        PutPlayer putPlayer = new PutPlayer(mContext, this, imageView, ANIMATION_DURATION, coord);
        mAnimationHandler.sendMessage(mAnimationHandler
                .obtainMessage(AnimationHandler.ADD_ANIMATION, putPlayer));
    }

    public void movePlayer(ImageView imageView, Coord coordInit, Coord coordDest, boolean isChoosed,
                           int duration) {
        PlayerMove playerMove = new PlayerMove(mContext, this, imageView,
                ANIMATION_DURATION, coordInit, coordDest, isChoosed);
        mAnimationHandler.sendMessage(mAnimationHandler
                .obtainMessage(AnimationHandler.ADD_ANIMATION, playerMove));
    }

    public void removePlayer(ImageView imageView) {
        PlayerDeath playerDeath = new PlayerDeath(mContext, this, imageView, ANIMATION_DURATION);
        mAnimationHandler.sendMessage(mAnimationHandler
                .obtainMessage(AnimationHandler.ADD_ANIMATION, playerDeath));
    }

    public void putArrow(ImageView imageView, Coord coordInit, Coord coordDest) {
        PutArrow putArrow = new PutArrow(mContext, this, imageView, ANIMATION_DURATION, coordInit, coordDest);
        mAnimationHandler.sendMessage(mAnimationHandler
                .obtainMessage(AnimationHandler.ADD_ANIMATION, putArrow));
    }

    public void hitArrow(ArrowView imageView, String state) {
        HitArrow hitArrow = new HitArrow(mContext, this,imageView, ANIMATION_DURATION, state);
        mAnimationHandler.sendMessage(mAnimationHandler
                .obtainMessage(AnimationHandler.ADD_ANIMATION, hitArrow));
    }

    public void nextAnimation() {
        if(mAnimationQueue == null || mAnimationQueue.isEmpty()) {
            return;
        }
        AnimationQueueElement animationQueueElement = mAnimationQueue.get(0);
        animationQueueElement.animate();
    }

    @Override
    public void onAnimationStart() {
        AnimationQueueElement element = mAnimationQueue.get(0);
        SoundHelper.playSound(element.getSoundId());
    }

    @Override
    public void onAnimationEnd() {
        AnimationQueueElement element = mAnimationQueue.remove(0);
        nextAnimation();
    }

    private static class AnimationHandler extends Handler {
        private static final int ADD_ANIMATION = 0;

        private WeakReference<BoardAnimationManager> mBoardAnimation;

        public AnimationHandler(BoardAnimationManager boardAnimationManager) {
            super();
            mBoardAnimation = new WeakReference<BoardAnimationManager>(boardAnimationManager);
        }

        @Override
        public void handleMessage(Message inputMessage) {
            BoardAnimationManager boardAnimationManager = mBoardAnimation.get();
            if(boardAnimationManager == null) {
                return;
            }
            switch (inputMessage.what) {
                case ADD_ANIMATION:
                    AnimationQueueElement animationQueueElement = (AnimationQueueElement)inputMessage.obj;
                    boardAnimationManager.mAnimationQueue.add(animationQueueElement);
                    if(boardAnimationManager.mAnimationQueue.size() == 1) {
                        boardAnimationManager.nextAnimation();
                    }
                    break;
            }
        }
    }
}
