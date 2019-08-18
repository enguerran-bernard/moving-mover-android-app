package com.movingmover.oem.movingmover.battle;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.movingmover.oem.movingmover.R;
import com.movingmover.oem.movingmover.helper.Arrow;
import com.movingmover.oem.movingmover.helper.Coord;
import com.movingmover.oem.movingmover.helper.MovingMoverHandlerMessages;
import com.movingmover.oem.movingmover.helper.MovingMoverIntent;
import com.movingmover.oem.movingmover.helper.Player;
import com.movingmover.oem.movingmover.sound.SoundHelper;
import com.movingmover.oem.movingmover.sound.SoundReadyListener;
import com.movingmover.oem.movingmover.webservice.MovingMoverService;
import com.movingmover.oem.movingmover.widget.ArrowView;
import com.movingmover.oem.movingmover.widget.BoardView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

public class BattleActivity extends Activity implements ControllerListener, SoundReadyListener {

    private static final String TAG = BattleActivity.class.getSimpleName();

    private Handler mMainThreadHandler;

    private TextView mTitleView;
    private BoardView mBoardView;
    private String mFirstIAName;
    private String mSecondIAName;
    private Controller mController;
    private byte[] mDefaultPlayerIcon;
    private HashMap<Integer, Player> mPlayerList;
    private ArrayList<Arrow> mArrowList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        mTitleView = findViewById(R.id.battle_activity_title);
        mBoardView = findViewById(R.id.board_view);
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String endPoint = preferences.getString(MovingMoverService.SERVER_IP_PREFERENCE, MovingMoverService.ENDPOINT);
        mController = new Controller(endPoint);
        mController.addListener(this);
        mMainThreadHandler = new MainThreadHandler(this);
        mPlayerList = new HashMap<>();
        mArrowList = new ArrayList<>();
        initIAView();
        initSounds();
    }

    @Override
    public void onDestroy() {
        mController.removeListener(this);
        super.onDestroy();
    }

    private void initIAView() {
        Intent intent = getIntent();
        mFirstIAName = intent.getStringExtra(MovingMoverIntent.EXTRA_IA1_NAME);
        mSecondIAName = intent.getStringExtra(MovingMoverIntent.EXTRA_IA2_NAME);
        mDefaultPlayerIcon = intent.getByteArrayExtra(MovingMoverIntent.EXTRA_IA2_ICON);
        mTitleView.setText(getResources().getString(R.string.battle_activity_title, mFirstIAName, mSecondIAName));
    }

    private void initSounds() {
        SoundHelper.loadSounds(this, this);
    }

    @Override
    public void onSoundsLoaded() {
        initBoardData();
    }

    private void initBoardData() {
        mBoardView.post(new Runnable() {
            @Override
            public void run() {
                mController.getGame();
            }
        });
    }

    private Bitmap getBitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    private static class MainThreadHandler extends Handler{
        public static final int PLACEMENT_PLAYER = 0;
        public static final int DEAD_PLAYER = 1;
        public static final int MOVE_PLAYER = 2;
        public static final int PUT_ARROW = 3;
        public static final int HIT_ARROW = 4;

        private WeakReference<BattleActivity> mActivity;

        public MainThreadHandler(BattleActivity activity) {
            super();
            mActivity = new WeakReference<BattleActivity>(activity);
        }

        @Override
        public void handleMessage(Message inputMessage) {
            BattleActivity activity = mActivity.get();
            if(activity == null) {
                return;
            }
            switch (inputMessage.what) {
                case PLACEMENT_PLAYER:
                    MovingMoverHandlerMessages.PlayerPositionMessage playerPositionMessage =
                            (MovingMoverHandlerMessages.PlayerPositionMessage) inputMessage.obj;
                    ImageView playerView = new ImageView(activity);
                    Coord playerCoord = new Coord(playerPositionMessage.x, playerPositionMessage.y);

                    playerView.setImageBitmap(activity.getBitmap(activity.mDefaultPlayerIcon));
                    if (activity.mPlayerList.size() == 1) {
                        int whiteColor = Color.parseColor("#FFFFFF");
                        PorterDuff.Mode mMode = PorterDuff.Mode.SRC_ATOP;
                        Drawable d = playerView.getDrawable();
                        d.setColorFilter(whiteColor, mMode);
                        activity.mPlayerList.put(playerPositionMessage.idPlayer,
                                new Player(playerPositionMessage.idPlayer, whiteColor, playerCoord, playerView));
                    }

                    else if (activity.mPlayerList.size() == 0) {
                        int blackColor = Color.parseColor("#000000");
                        PorterDuff.Mode mMode = PorterDuff.Mode.SRC_ATOP;
                        Drawable d = playerView.getDrawable();
                        d.setColorFilter(blackColor, mMode);
                        activity.mPlayerList.put(playerPositionMessage.idPlayer,
                                new Player(playerPositionMessage.idPlayer, blackColor, playerCoord, playerView));
                    }

                    else if (activity.mPlayerList.size() == 2) {
                        int greenColor = Color.parseColor("#00FF00");
                        PorterDuff.Mode mMode = PorterDuff.Mode.SRC_ATOP;
                        Drawable d = playerView.getDrawable();
                        d.setColorFilter(greenColor, mMode);
                        activity.mPlayerList.put(playerPositionMessage.idPlayer,
                                new Player(playerPositionMessage.idPlayer, greenColor, playerCoord, playerView));
                    }

                    else if (activity.mPlayerList.size() == 3) {
                        int blueColor = Color.parseColor("#0000FF");
                        PorterDuff.Mode mMode = PorterDuff.Mode.SRC_ATOP;
                        Drawable d = playerView.getDrawable();
                        d.setColorFilter(blueColor, mMode);
                        activity.mPlayerList.put(playerPositionMessage.idPlayer,
                                new Player(playerPositionMessage.idPlayer, blueColor, playerCoord, playerView));
                    }

                    playerView.setVisibility(View.INVISIBLE);

                    activity.addContentView(playerView, new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));

                    activity.mBoardView.putPlayer(playerView, playerCoord);
                    break;
                case DEAD_PLAYER:
                    MovingMoverHandlerMessages.PlayerDeadMessage playerDeadMessage =
                            (MovingMoverHandlerMessages.PlayerDeadMessage) inputMessage.obj;
                    Player deadPlayer = activity.mPlayerList.remove(playerDeadMessage.idPlayer);
                    activity.mBoardView.removePlayer(deadPlayer.getImageView());
                    break;
                case MOVE_PLAYER:
                    MovingMoverHandlerMessages.PlayerMoveMessage playerMoveMessage =
                            (MovingMoverHandlerMessages.PlayerMoveMessage) inputMessage.obj;
                    Player movedPlayer = activity.mPlayerList.get(playerMoveMessage.idPlayer);
                    ImageView movedView = movedPlayer.getImageView();
                    Coord coordMovePlayer = movedPlayer.getCoord();
                    if(movedView == null) {
                        Log.i(TAG, "player " + playerMoveMessage.idPlayer + " has no view to move");
                    }
                    int destMovePlayerX = 0;
                    int destMovePlayerY = 0;
                    switch (playerMoveMessage.direction) {
                        case "UP":
                            destMovePlayerX = coordMovePlayer.x;
                            destMovePlayerY = coordMovePlayer.y - 1;
                            break;
                        case "DOWN":
                            destMovePlayerX = coordMovePlayer.x;
                            destMovePlayerY = coordMovePlayer.y + 1;
                            break;
                        case "LEFT":
                            destMovePlayerX = coordMovePlayer.x - 1;
                            destMovePlayerY = coordMovePlayer.y;
                            break;
                        case "RIGHT":
                            destMovePlayerX = coordMovePlayer.x + 1;
                            destMovePlayerY = coordMovePlayer.y;
                            break;
                    }
                    movedPlayer.setCoord(new Coord(destMovePlayerX, destMovePlayerY));
                    activity.mBoardView.movePlayer(movedView, new Coord(coordMovePlayer.x, coordMovePlayer.y),
                            new Coord(destMovePlayerX, destMovePlayerY),
                            playerMoveMessage.isChoosed, 500);
                    break;
                case PUT_ARROW:
                    MovingMoverHandlerMessages.ArrowPutMessage arrowPutMessage =
                            (MovingMoverHandlerMessages.ArrowPutMessage) inputMessage.obj;
                    ArrowView arrowView = new ArrowView(activity.getApplicationContext());
                    Player playerArrow = activity.mPlayerList.get(arrowPutMessage.idPlayer);
                    switch (arrowPutMessage.orientation) {
                        case "UP":
                            arrowView.setOrientation(ArrowView.ORIENTATION.UP);
                            break;
                        case "DOWN":
                            arrowView.setOrientation(ArrowView.ORIENTATION.DOWN);
                            break;
                        case "LEFT":
                            arrowView.setOrientation(ArrowView.ORIENTATION.LEFT);
                            break;
                        case "RIGHT":
                            arrowView.setOrientation(ArrowView.ORIENTATION.RIGHT);
                            break;
                    }
                    Coord coordInit = playerArrow.getCoord();
                    int destX = coordInit.x;
                    int destY = coordInit.y;
                    switch (arrowPutMessage.direction) {
                        case "UP":
                            destY += -1;
                            break;
                        case "DOWN":
                            destY += 1;
                            break;
                        case "LEFT":
                            destX += -1;
                            break;
                        case "RIGHT":
                            destX += 1;
                            break;
                    }
                    arrowView.setColorThree( activity.mPlayerList.get(arrowPutMessage.idPlayer).getColor());
                    arrowView.setVisibility(View.INVISIBLE);
                    activity.addContentView(arrowView, new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
                    Arrow arrowPut = new Arrow("", new Coord(destX, destY), arrowView);
                    activity.mArrowList.add(arrowPut);
                    activity.mBoardView.putArrow(arrowView, coordInit, new Coord(destX, destY));
                    break;
                case HIT_ARROW:
                    MovingMoverHandlerMessages.ArrowHitMessage arrowHitMessage =
                            (MovingMoverHandlerMessages.ArrowHitMessage) inputMessage.obj;
                    Arrow hitArrow = null;
                    if(activity.mArrowList != null) {
                        for(Arrow arrowMovedElement : activity.mArrowList) {
                            Coord arrowMovedElementCoord = arrowMovedElement.getCoord();
                            if(arrowMovedElementCoord.x == arrowHitMessage.x
                                    && arrowMovedElementCoord.y == arrowHitMessage.y) {
                                hitArrow = arrowMovedElement;
                                hitArrow.setState(arrowHitMessage.state);
                                activity.mBoardView.hitArrow(arrowHitMessage.state, hitArrow.getmImageView());
                                break;
                            }
                        }
                    }
                    if(hitArrow != null && arrowHitMessage.state.equals("DESTROYED")) {
                        activity.mArrowList.remove(hitArrow);
                    }
                    break;
                default:
                    //nothing to do
            }
        }
    }

    @Override
    public void onMapDimensionReceived(int x, int y) {
        mBoardView.setSize(x);
    }

    @Override
    public void onPlayerInitialPosition(int x, int y, int idPlayer, int duration) {
        MovingMoverHandlerMessages.PlayerPositionMessage positionMessage =
                new MovingMoverHandlerMessages.PlayerPositionMessage();
        positionMessage.idPlayer = idPlayer;
        positionMessage.x = x;
        positionMessage.y = y;
        mMainThreadHandler.sendMessage(mMainThreadHandler.obtainMessage
                (MainThreadHandler.PLACEMENT_PLAYER, positionMessage));
    }

    @Override
    public void onPlayerDead(int idPlayer, int duration) {
        MovingMoverHandlerMessages.PlayerDeadMessage playerDeadMessage =
                new MovingMoverHandlerMessages.PlayerDeadMessage();
        playerDeadMessage.idPlayer = idPlayer;
        mMainThreadHandler.sendMessage(mMainThreadHandler.obtainMessage
                (MainThreadHandler.DEAD_PLAYER, playerDeadMessage));
    }

    @Override
    public void onMoveAction(String direction, int idPlayer, boolean isChoosed, int duration) {
        MovingMoverHandlerMessages.PlayerMoveMessage playerMoveMessage =
                new MovingMoverHandlerMessages.PlayerMoveMessage();
        playerMoveMessage.idPlayer = idPlayer;
        playerMoveMessage.direction = direction;
        playerMoveMessage.isChoosed = isChoosed;
        mMainThreadHandler.sendMessage(mMainThreadHandler.obtainMessage
                (MainThreadHandler.MOVE_PLAYER, playerMoveMessage));
    }

    @Override
    public void onArrowPut(int idPlayer, String orientation, String direction, int duration) {
        MovingMoverHandlerMessages.ArrowPutMessage arrowPutMessage =
                new MovingMoverHandlerMessages.ArrowPutMessage();
        arrowPutMessage.direction = direction;
        arrowPutMessage.orientation = orientation;
        arrowPutMessage.idPlayer = idPlayer;
        mMainThreadHandler.sendMessage(mMainThreadHandler.obtainMessage
                (MainThreadHandler.PUT_ARROW, arrowPutMessage));
    }

    @Override
    public void onArrowHit(int x, int y, String state, int duration) {
        MovingMoverHandlerMessages.ArrowHitMessage arrowHitMessage =
                new MovingMoverHandlerMessages.ArrowHitMessage();
        arrowHitMessage.state = state;
        arrowHitMessage.x = x;
        arrowHitMessage.y = y;
        mMainThreadHandler.sendMessage(mMainThreadHandler.obtainMessage
                (MainThreadHandler.HIT_ARROW, arrowHitMessage));
    }

}
