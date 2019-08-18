package com.movingmover.oem.movingmover.login;

import com.movingmover.oem.movingmover.webservice.ServiceController;
import com.movingmover.oem.movingmover.webservice.ServiceControllerListener;
import com.movingmover.oem.movingmover.webservice.data.Credentials;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Controller extends ServiceController<ControllerListener> {

    public Controller(String endPoint) {
        super(endPoint);
    }

    public void connect(String id, String password) {
        Credentials credentials = new Credentials();
        credentials.eMail = id;
        credentials.pwd = password;
        mService.connect(credentials).enqueue(new Callback<Boolean>() {

            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Boolean res = response.body();
                if(res != null) {
                    for(ControllerListener listener : mListeners) {
                        listener.onConnectionResult(res, ServiceControllerListener.SERVER_FOUND);
                    }
                    return;
                }
                for(ControllerListener listener : mListeners) {
                    listener.onConnectionResult(false, ServiceControllerListener.SERVER_NOT_FOUND);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                for(ControllerListener listener : mListeners) {
                    listener.onConnectionResult(false, ServiceControllerListener.SERVER_NOT_FOUND);
                }
            }
        });
    }
}
