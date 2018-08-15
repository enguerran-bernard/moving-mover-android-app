package com.movingmover.oem.movingmover.webservice.data_management;

public interface GameEventListener {
    public void onPlayerInitialPosition(int x, int y, int idPlayer, int duration);
    public void onPlayerDead(int idPlayer, int duration);
    public void onMoveAction(String direction, int idPlayer, boolean isChoosed, int duration);
    public void onArrowPut(int idPlayer, String orientation, String direction, int duration);
    public void onArrowHit(int x, int y, String state, int duration);
}
