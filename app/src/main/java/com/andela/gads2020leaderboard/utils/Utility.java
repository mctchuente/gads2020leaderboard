package com.andela.gads2020leaderboard.utils;

import com.andela.gads2020leaderboard.service.APIClient;
import com.andela.gads2020leaderboard.service.APIInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Utility
 *
 * @author Bertrand TCHUENTE
 */
public class Utility {
    private static final String LOG_TAG = Utility.class.getSimpleName();
    public static final String API_SERVER_URL = "https://gadsapi.herokuapp.com";

    public static Gson getGson(){
        return getGsonBuilder().create();
    }

    public static GsonBuilder getGsonBuilder(){
        return new GsonBuilder();
    }

    public static APIInterface getApiInterface(){
        return APIClient.getClient().create(APIInterface.class);
    }

    public static APIInterface getApiInterface(String baseUrl){
        return APIClient.getClient(baseUrl).create(APIInterface.class);
    }
}
