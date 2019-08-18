package com.movingmover.oem.movingmover.webservice.deserializer;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.movingmover.oem.movingmover.webservice.data.ActionHitArrow;
import com.movingmover.oem.movingmover.webservice.data.ActionMove;
import com.movingmover.oem.movingmover.webservice.data.ActionPlayerDead;
import com.movingmover.oem.movingmover.webservice.data.ActionPutArrow;
import com.movingmover.oem.movingmover.webservice.data.GameEvent;
import com.movingmover.oem.movingmover.webservice.data.PlacementPlayer;

import java.lang.reflect.Type;

public class GameEventDeserializer implements JsonDeserializer<GameEvent> {

    private static final String TAG = GameEventDeserializer.class.getSimpleName();

    private static final String PLACEMENT_PLAYER = "PlacementPlayerDto";
    private static final String ACTION_PLAYER_DEAD = "DeathPlayerDto";
    private static final String ACTION_MOVE = "ActionMoveDto";
    private static final String ACTION_PUT_ARROW = "ActionPutArrowDto";
    private static final String ACTION_HIT_ARROW = "HitArrowDto";

    private static final String EVENT_KIND = "idClasse";
    private static final String ID_PLAYER = "idPlayer";
    private static final String POSITION_X = "x";
    private static final String POSITION_Y = "y";
    private static final String ACTION_DIRECTION = "direction";
    private static final String ACTION_ORIENTATION = "arrowDirection";
    private static final String MOVE_CHOOSED = "choosed";
    private static final String STATE = "state";

    @Override
    public GameEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Log.i(TAG, "Using a custom deserializer for LGame " + json);
        Gson gson = new Gson();
        GameEvent gameEvent = new GameEvent();
        final JsonObject jsonObject = json.getAsJsonObject();
        String eventKind = jsonObject.get(EVENT_KIND).getAsString();
        switch(eventKind) {
            case PLACEMENT_PLAYER:
                PlacementPlayer placementPlayer = new PlacementPlayer();
                placementPlayer.idClasse = eventKind;
                placementPlayer.idPlayer = jsonObject.get(ID_PLAYER).getAsInt();
                placementPlayer.x = jsonObject.get(POSITION_X).getAsInt();
                placementPlayer.y = jsonObject.get(POSITION_Y).getAsInt();
                gameEvent = placementPlayer;
                break;
            case ACTION_PLAYER_DEAD:
                ActionPlayerDead actionPlayerDead = new ActionPlayerDead();
                actionPlayerDead.idPlayer = jsonObject.get(ID_PLAYER).getAsInt();
                gameEvent = actionPlayerDead;
                break;
            case ACTION_MOVE:
                ActionMove actionMove = new ActionMove();
                actionMove.idClasse = eventKind;
                actionMove.idPlayer = jsonObject.get(ID_PLAYER).getAsInt();
                actionMove.direction = jsonObject.get(ACTION_DIRECTION).getAsString();
                actionMove.isChoosed = jsonObject.get(MOVE_CHOOSED).getAsBoolean();
                gameEvent = actionMove;
                break;
            case ACTION_PUT_ARROW:
                ActionPutArrow actionPutArrow = new ActionPutArrow();
                actionPutArrow.direction = jsonObject.get(ACTION_DIRECTION).getAsString();
                actionPutArrow.orientation = jsonObject.get(ACTION_ORIENTATION).getAsString();
                actionPutArrow.idPlayer = jsonObject.get(ID_PLAYER).getAsInt();
                gameEvent = actionPutArrow;
                break;
            case ACTION_HIT_ARROW:
                ActionHitArrow actionHitArrow = new ActionHitArrow();
                actionHitArrow.state =jsonObject.get(STATE).getAsString();
                actionHitArrow.x = jsonObject.get(POSITION_X).getAsInt();
                actionHitArrow.y = jsonObject.get(POSITION_Y).getAsInt();
                gameEvent = actionHitArrow;
            default:
                break;
        }
        return gameEvent;
    }
}
