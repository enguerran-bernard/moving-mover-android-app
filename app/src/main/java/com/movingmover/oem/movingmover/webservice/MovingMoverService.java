package com.movingmover.oem.movingmover.webservice;

import com.movingmover.oem.movingmover.webservice.data.Credentials;
import com.movingmover.oem.movingmover.webservice.data.Game;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MovingMoverService {
    String ENDPOINT = "http://192.168.1.93:8181";
    String SERVER_IP_PREFERENCE = "serverippreference";

    @POST("/MovingMover/authentication")
    Call<Boolean> connect(@Body Credentials credentials);

    @GET("/MovingMover/getGame")
    Call<Game> getGame();
}
