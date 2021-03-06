package com.movingmover.oem.movingmover.battle;


import com.movingmover.oem.movingmover.webservice.ServiceController;
import com.movingmover.oem.movingmover.webservice.data.Game;
import com.movingmover.oem.movingmover.webservice.data.GameEvent;
import com.movingmover.oem.movingmover.webservice.data.GameHistory;
import com.movingmover.oem.movingmover.webservice.data.MapSize;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Controller extends ServiceController<ControllerListener> implements GameEventListener {

    private static final String TAG = Controller.class.getSimpleName();

    public Controller(String endPoint) {
        super(endPoint);
    }

    public void getGame() {
        mService.getGame().enqueue(new Callback<Game>() {

            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                Game game = response.body();
                if(game != null) {
                    MapSize mapSize = game.mapDto;
                    for(ControllerListener listener : mListeners) {
                        listener.onMapDimensionReceived(mapSize.width, mapSize.height);
                    }
                    List<GameEvent> events = new ArrayList<GameEvent>();
                    for(GameHistory gameHistory : game.histoEventsDto) {
                        events.add(gameHistory.eventDto);
                    }
                    GameEventManager manager = new GameEventManager(events, Controller.this);
                }
            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {

            }
        });
    }

    @Override
    public void onPlayerInitialPosition(int x, int y, int idPlayer, int duration) {
        for(ControllerListener listener : mListeners) {
            listener.onPlayerInitialPosition(x, y, idPlayer, duration);
        }
    }

    @Override
    public void onPlayerDead(int idPlayer, int duration) {
        for(ControllerListener listener : mListeners) {
            listener.onPlayerDead(idPlayer, duration);
        }
    }

    @Override
    public void onMoveAction(String direction, int idPlayer, boolean isChoosed, int duration) {
        for(ControllerListener listener : mListeners) {
            listener.onMoveAction(direction, idPlayer, isChoosed, duration);
        }
    }

    @Override
    public void onArrowPut(int idPlayer, String orientation, String direction, int duration) {
        for(ControllerListener listener : mListeners) {
            listener.onArrowPut(idPlayer, orientation, direction, duration);
        }
    }

    @Override
    public void onArrowHit(int x, int y, String state, int duration) {
        for(ControllerListener listener : mListeners) {
            listener.onArrowHit(x, y, state, duration);
        }
    }

}
