package com.movingmover.oem.movingmover.login;

import android.support.v4.app.Fragment;

public abstract class LoginFragment extends Fragment implements ControllerListener {

    @Override
    public void onConnectionResult(boolean res, int resCode) {
        // nothing to do
    }
}
