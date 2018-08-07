package com.test.android.moviespreview;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

public class MoviesLoader extends AsyncTaskLoader<ArrayList<Movie>> {
    String url;

    public MoviesLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Movie> loadInBackground() {
        return QueryUtils.fetchData(url);
    }

    @Override
    protected void onReset() {
        super.onReset();
    }
}
