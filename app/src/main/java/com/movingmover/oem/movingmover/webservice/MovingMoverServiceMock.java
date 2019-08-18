package com.movingmover.oem.movingmover.webservice;

import com.movingmover.oem.movingmover.webservice.data.ActionMove;
import com.movingmover.oem.movingmover.webservice.data.ActionPlayerDead;
import com.movingmover.oem.movingmover.webservice.data.Credentials;
import com.movingmover.oem.movingmover.webservice.data.Game;
import com.movingmover.oem.movingmover.webservice.data.GameHistory;
import com.movingmover.oem.movingmover.webservice.data.MapSize;
import com.movingmover.oem.movingmover.webservice.data.PlacementPlayer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

public class MovingMoverServiceMock implements MovingMoverService {
    public static final String ENDPOINT = "http://192.168.137.1:8181";
    BehaviorDelegate<MovingMoverService> mDelegate;
    public MovingMoverServiceMock(BehaviorDelegate<MovingMoverService> delegate) {
        mDelegate = delegate;
    }


    @Override
    public Call<Game> getGame() {
        Game game = new Game();
        MapSize mapSize = new MapSize();
        mapSize.height = 5;
        mapSize.width = 5;
        game.mapDto = mapSize;
        List<GameHistory> events = new ArrayList<GameHistory>();

        GameHistory gameHistory1 = new GameHistory();
        gameHistory1.timeExecution = 10;
        PlacementPlayer placementPlayer = new PlacementPlayer();
        placementPlayer.idClasse = "PlacementPlayerDto";
        placementPlayer.idPlayer = 1;
        placementPlayer.x = 1;
        placementPlayer.y = 1;
        gameHistory1.eventDto = placementPlayer;
        events.add(gameHistory1);

        for(int i = 0; i < 4; i++) {/*
            if(i % 2 == 0) {
                GameHistory gameHistory2 = new GameHistory();
                gameHistory2.timeExecution = 10;
                ActionPutArrow gameEvent2 = new ActionPutArrow();
                gameEvent2.idClasse = "ActionPutArrowDto";
                gameEvent2.direction = "DOWN";
                gameEvent2.idPlayer = 1;
                gameEvent2.orientation = "DOWN";
                gameHistory2.eventDto = gameEvent2;
                events.add(gameHistory2);
            }*/
            /*
            GameHistory gameHistory3 = new GameHistory();
            gameHistory3.timeExecution = 10;
            ActionMove gameEvent3 = new ActionMove();
            gameEvent3.idClasse = "ActionMoveDto";
            gameEvent3.direction = "DOWN";
            gameEvent3.idPlayer = 1;
            gameEvent3.isChoosed = true;
            gameHistory3.eventDto = gameEvent3;
            events.add(gameHistory3);
*/
            /*
            GameHistory gameHistory4 = new GameHistory();
            gameHistory4.timeExecution = 10;
            ActionMove gameEvent4 = new ActionMove();
            gameEvent4.idClasse = "ActionMoveDto";
            gameEvent4.direction = "LEFT";
            gameEvent4.idPlayer = 1;
            gameEvent4.isChoosed = false;
            gameHistory4.eventDto = gameEvent4;
            events.add(gameHistory4);*/

            GameHistory gameHistory7 = new GameHistory();
            gameHistory7.timeExecution = 10;
            ActionMove gameEvent7 = new ActionMove();
            gameEvent7.idClasse = "ActionMoveDto";
            gameEvent7.direction = "DOWN";
            gameEvent7.idPlayer = 1;
            gameEvent7.isChoosed = true;
            gameHistory7.eventDto = gameEvent7;
            events.add(gameHistory7);
/*
            GameHistory gameHistory8 = new GameHistory();
            gameHistory8.timeExecution = 10;
            ActionMove gameEvent8 = new ActionMove();
            gameEvent8.idClasse = "ActionMoveDto";
            gameEvent8.direction = "DOWN";
            gameEvent8.idPlayer = 1;
            gameEvent8.isChoosed = false;
            gameHistory8.eventDto = gameEvent8;
            events.add(gameHistory8);*/
/*
            GameHistory gameHistory9 = new GameHistory();
            gameHistory9.timeExecution = 10;
            ActionHitArrow gameEvent9 = new ActionHitArrow();
            gameEvent9.idClasse = "ActionHitArrowDto";
            gameEvent9.state = "DESTROYED";
            gameEvent9.x = 2;
            gameEvent9.y = 2;
            gameHistory9.eventDto = gameEvent9;
            events.add(gameHistory9);*/
/*if (i % 2 != 0) {
    GameHistory gameHistory6 = new GameHistory();
    gameHistory6.timeExecution = 10;
    ActionHitArrow gameEvent6 = new ActionHitArrow();
    gameEvent6.idClasse = "ActionHitArrowDto";
    gameEvent6.state = "DAMAGED";
    gameEvent6.x = 2;
    gameEvent6.y = 2;
    gameHistory6.eventDto = gameEvent6;
    events.add(gameHistory6);
}*/
        }

        GameHistory gameHistory10 = new GameHistory();
        gameHistory10.timeExecution = 10;
        ActionPlayerDead actionPlayerDead = new ActionPlayerDead();
        actionPlayerDead.idClasse = "DeadPlayerDto";
        actionPlayerDead.idPlayer = 1;
        gameHistory10.eventDto = actionPlayerDead;
        events.add(gameHistory10);

/*
        GameHistory gameHistory5 = new GameHistory();
        gameHistory5.timeExecution = 10;
        ActionPutArrow gameEvent5 = new ActionPutArrow();
        gameEvent5.idClasse = "ActionPutArrowDto";
        gameEvent5.direction = "DOWN";
        gameEvent5.orientation="UP";
        gameEvent5.idPlayer = 0;
        gameHistory5.eventDto = gameEvent5;
        events.add(gameHistory5);
        */

/*
        GameHistory gameHistory2= new GameHistory();
        gameHistory2.timeExecution = 10;
        ActionMove gameEvent = new ActionMove();
        gameEvent.idClasse = "ActionMoveDto";
        gameEvent.direction = "UP";
        gameEvent.idPlayer = 0;
        gameEvent.isChoosed = true;
        gameHistory2.eventDto = gameEvent;
        events.add(gameHistory2);
*/
        game.histoEventsDto = events;
        return mDelegate.returningResponse(game).getGame();
    }

    @Override
    public Call<Boolean> connect(Credentials credentials) {
        return mDelegate.returningResponse(new Boolean(Boolean.FALSE)).connect(credentials);
    }
}
