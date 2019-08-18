package com.movingmover.oem.movingmover.login;

import com.movingmover.oem.movingmover.webservice.ServiceControllerListener;

public interface ControllerListener extends ServiceControllerListener {
    void onConnectionResult(boolean res, int resCode);
}
