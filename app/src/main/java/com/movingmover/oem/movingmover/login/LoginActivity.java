package com.movingmover.oem.movingmover.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.movingmover.oem.movingmover.animation.MutedVideoView;
import com.movingmover.oem.movingmover.ia_selection.IASelectionActivity;
import com.movingmover.oem.movingmover.R;
import com.movingmover.oem.movingmover.webservice.MovingMoverService;

public class LoginActivity extends FragmentActivity implements ControllerListener {

    private static final String TAG = LoginActivity.class.getSimpleName();

    public static final int FRAGMENT_CONNECTION = 0;
    public static final int FRAGMENT_CREATE_ACCOUNT = 1;
    public static final int FRAGMENT_FORGOTTEN_PASSWORD = 2;

    private LinearLayout mLoginScreen;
    private MutedVideoView mVideoView;
    private ImageView mTitleImageView;

    private Controller mController;
    private LoginFragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        mLoginScreen = findViewById(R.id.login_screen);
        mTitleImageView = findViewById(R.id.login_activity_title);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String endPoint = preferences.getString(MovingMoverService.SERVER_IP_PREFERENCE, MovingMoverService.ENDPOINT);
        mController = new Controller(endPoint);
        mController.addListener(this);
        switchToFragment(FRAGMENT_CONNECTION);

        mVideoView = findViewById(R.id.login_video_view);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.test);

        mVideoView.setVideoURI(uri);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        mVideoView.start();
    }

    @Override
    public void onStart() {
        super.onStart();
        mVideoView.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        mVideoView.stopPlayback();
    }

    public Controller getController() {
        return mController;
    }

    public void switchToFragment(int fragmentId) {
        LoginFragment destinationFragment = null;
        switch (fragmentId) {
            case FRAGMENT_CONNECTION:
                destinationFragment = new ConnectionFragment();
                break;
            case FRAGMENT_CREATE_ACCOUNT:
                destinationFragment = new CreateAccountFragment();
                break;
            case FRAGMENT_FORGOTTEN_PASSWORD:
                destinationFragment = new ForgottenPasswordFragment();
                break;
            default:
                Log.w(TAG, "unknown fragment: " + fragmentId);
                break;
        }
        if (destinationFragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container_login, destinationFragment);
            transaction.commit();
            mCurrentFragment = destinationFragment;
        }
    }

    @Override
    public void onConnectionResult(boolean res, int resCode) {
        if (mCurrentFragment != null) {
            mCurrentFragment.onConnectionResult(res, resCode);
        }
        if (res) {
            Intent intent = new Intent(this, IASelectionActivity.class);
            startActivity(intent);
        }
    }
}
