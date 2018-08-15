package com.test.android.moviespreview;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    private static MoviesDBAPI moviesDBAPI;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        moviesDBAPI = retrofit.create(MoviesDBAPI.class);
    }

    public static MoviesDBAPI getApi() {
        return moviesDBAPI;
    }
}
