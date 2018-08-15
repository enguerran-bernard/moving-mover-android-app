package com.movingmover.oem.movingmover.helper;

public class MovingMoverHandlerMessages {
    public static class PlayerPositionMessage {
        public int x;
        public int y;
        public int idPlayer;
    }

    public static class PlayerDeadMessage {
        public int idPlayer;
    }

    public static class PlayerMoveMessage {
        public String direction;
        public int idPlayer;
        public boolean isChoosed;
    }

    public static class ArrowPutMessage {
        public String orientation;
        public String direction;
        public int idPlayer;
    }

    public static class ArrowHitMessage {
        public int x;
        public int y;
        public String state;
    }
}
