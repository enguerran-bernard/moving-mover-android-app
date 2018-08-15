package com.movingmover.oem.movingmover.webservice.data_management;


import android.os.Handler;
import android.os.HandlerThread;

import com.movingmover.oem.movingmover.webservice.data.ActionHitArrow;
import com.movingmover.oem.movingmover.webservice.data.ActionMove;
import com.movingmover.oem.movingmover.webservice.data.ActionPlayerDead;
import com.movingmover.oem.movingmover.webservice.data.ActionPutArrow;
import com.movingmover.oem.movingmover.webservice.data.GameEvent;
import com.movingmover.oem.movingmover.webservice.data.PlacementPlayer;

import java.util.List;

public class GameEventManager {
    private static final String TAG = GameEvent.class.getSimpleName();
    private static final String BACKGROUND_THREAD_ID = "GameEventManagerThreadId";
    private static final int EVENT_DELAY= 1000;

    private final List<GameEvent> mEvents;
    private final GameEventListener mGameEventListener;
    private HandlerThread mBackgroundHandlerThread;
    private Handler mBackgroundHandler;
    private Runnable mGameEventRunnable;


    public GameEventManager(List<GameEvent> events, GameEventListener listener) {
        mEvents = events;
        mGameEventListener = listener;
        mBackgroundHandlerThread = new HandlerThread(BACKGROUND_THREAD_ID);
        mBackgroundHandlerThread.start();
        mBackgroundHandler = new Handler(mBackgroundHandlerThread.getLooper());
        mGameEventRunnable = new Runnable() {
            @Override
            public void run() {
                if(mEvents != null && !mEvents.isEmpty()) {
                    GameEvent gameEvent = mEvents.remove(0);
                    int delay = manageGameEvent(gameEvent);
                    mBackgroundHandler.postDelayed(this, delay);
                } else {
                    mBackgroundHandler.removeCallbacksAndMessages(null);
                    mBackgroundHandlerThread.quitSafely();
                }
            }
        };
        mBackgroundHandler.post(mGameEventRunnable);
    }

    private int manageGameEvent(GameEvent gameEvent) {
        if(gameEvent == null) {
            return 0;
        }
        if(gameEvent instanceof PlacementPlayer) {
            PlacementPlayer placementPlayer = (PlacementPlayer) gameEvent;
            mGameEventListener.onPlayerInitialPosition(placementPlayer.x, placementPlayer.y,
                    placementPlayer.idPlayer, EVENT_DELAY);
            return EVENT_DELAY;
        } else if(gameEvent instanceof ActionPlayerDead) {
            ActionPlayerDead actionPlayerDead = (ActionPlayerDead) gameEvent;
            mGameEventListener.onPlayerDead(actionPlayerDead.idPlayer, EVENT_DELAY);
            return EVENT_DELAY;
        }else if(gameEvent instanceof ActionMove) {
            ActionMove actionMove = (ActionMove) gameEvent;
            mGameEventListener.onMoveAction(actionMove.direction, actionMove.idPlayer, actionMove.isChoosed, EVENT_DELAY);
            return EVENT_DELAY;
        } else if(gameEvent instanceof ActionPutArrow) {
            ActionPutArrow actionPutArrow = (ActionPutArrow) gameEvent;
            mGameEventListener.onArrowPut(actionPutArrow.idPlayer, actionPutArrow.orientation,
                    actionPutArrow.direction, EVENT_DELAY);

        } else if(gameEvent instanceof ActionHitArrow) {
            ActionHitArrow actionHitArrow = (ActionHitArrow) gameEvent;
            mGameEventListener.onArrowHit(actionHitArrow.x, actionHitArrow.y,
                    actionHitArrow.state, EVENT_DELAY);
        }
        return 0;
    }
}
