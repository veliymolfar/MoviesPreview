package com.test.android.moviespreview;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesDBAPI {
    @GET("3/discover/movie")
    Call<ModelMovies> discoverMovie(@Query("api_key") String apiKey,
                                    @Query("sort_by") String sortBy);
}
