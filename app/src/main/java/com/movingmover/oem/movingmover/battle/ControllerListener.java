package com.movingmover.oem.movingmover.battle;

import com.movingmover.oem.movingmover.webservice.ServiceControllerListener;

public interface ControllerListener extends ServiceControllerListener {
    void onMapDimensionReceived(int x, int y);
    void onPlayerInitialPosition(int x, int y, int idPlayer, int duration);
    void onPlayerDead(int idPlayer, int duration);
    void onMoveAction(String direction, int idPlayer, boolean isChoosed, int duration);
    void onArrowPut(int idPlayer, String orientation, String direction, int duration);
    void onArrowHit(int x, int y, String state, int duration);
}
