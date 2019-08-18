package com.movingmover.oem.movingmover.webservice;

import java.util.ArrayList;

public abstract class ServiceController<T extends ServiceControllerListener> {

    private static final String TAG = ServiceController.class.getSimpleName();
    protected MovingMoverService mService;
    protected ArrayList<T> mListeners;

    public ServiceController(String endPoint) {
        mService = RetrofitUtil.getServiceMock(endPoint);
        mListeners = new ArrayList<>();
    }

    public void addListener(T listener) {
        mListeners.add(listener);
    }

    public void removeListener(T listener) {
        mListeners.remove(listener);
    }
}
