package com.movingmover.oem.movingmover.webservice;

import com.movingmover.oem.movingmover.webservice.data.Game;
import com.movingmover.oem.movingmover.webservice.data.MapSize;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovingMoverService {
    public static final String ENDPOINT = "http://192.168.1.86:8181";

    @GET("/MovingMover/getGame")
    Call<Game> getGame();
}
