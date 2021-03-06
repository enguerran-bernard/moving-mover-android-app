package com.movingmover.oem.movingmover.webservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.movingmover.oem.movingmover.webservice.data.GameEvent;
import com.movingmover.oem.movingmover.webservice.deserializer.GameEventDeserializer;

import java.util.concurrent.TimeUnit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

public class RetrofitUtil {
    public static MovingMoverService getService(String endPoint) {
        //SharedPreferences settings = getSharedPreferences(MovingMoverService.SERVER_IP_PREFERENCE, MODE_PRIVATE)
        return new Retrofit.Builder()
                .baseUrl(endPoint)
                .addConverterFactory(buildGameEventGsonConverter())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovingMoverService.class);
    }

    public static MovingMoverService getServiceMock(String endPoint) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(endPoint)
                .addConverterFactory(buildGameEventGsonConverter())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NetworkBehavior networkBehavior = NetworkBehavior.create();
        networkBehavior.setDelay(1000, TimeUnit.MILLISECONDS);
        networkBehavior.setVariancePercent(90);
        MockRetrofit mock = new MockRetrofit.Builder(retrofit)
                .networkBehavior(networkBehavior)
                .build();
        BehaviorDelegate<MovingMoverService> delegate = mock.create(MovingMoverService.class);
        return new MovingMoverServiceMock(delegate);
    }

    private static GsonConverterFactory buildGameEventGsonConverter() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(GameEvent.class, new GameEventDeserializer());
        Gson gson = gsonBuilder.create();

        return GsonConverterFactory.create(gson);
    }
}
