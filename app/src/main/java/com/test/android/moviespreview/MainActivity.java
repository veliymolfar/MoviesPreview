package com.test.android.moviespreview;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {
    private static final String TAG = MainActivity.class.getName();
    private ArrayList<Movie> mMovies;
    private String REQUEST_URL = "https://api.themoviedb.org/3/discover/movie?api_key=76e5c6b5615f8027e79416b12e55aa8a&language=sort_by=popularity.desc";
    private MoviesAdapter moviesAdapter;
    private View retryView;
    private Button retryBtn;
    private GridView gridView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retryView = findViewById(R.id.retry_view);
        retryBtn = findViewById(R.id.retry_btn);
        progressBar = findViewById(R.id.progress_bar);
        gridView = findViewById(R.id.grid_view);

        moviesAdapter = new MoviesAdapter(this, new ArrayList<Movie>());
        gridView.setAdapter(moviesAdapter);
        retryBtn.setOnClickListener(retryClick);
        mMovies = new ArrayList<>();

        if (savedInstanceState == null || !savedInstanceState.containsKey("movies")) {
            if (checkInternet()) {
                getSupportLoaderManager().initLoader(0, null, MainActivity.this);
                Log.d(TAG, "Start load movies");
            } else {
                progressBar.setVisibility(View.GONE);
                showInternetAttention();
            }
        } else {
            Log.d(TAG, "Restoring movies array");
            mMovies = savedInstanceState.getParcelableArrayList("movies");
            progressBar.setVisibility(View.GONE);
            moviesAdapter.clear();
            moviesAdapter.addAll(mMovies);
        }
    }

    @NonNull
    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int i, @Nullable Bundle bundle) {
        Log.d(TAG, "Create Loader");
        return new MoviesLoader(this, REQUEST_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Movie>> loader, ArrayList<Movie> movies) {
        mMovies = movies;
        progressBar.setVisibility(View.GONE);
        moviesAdapter.clear();
        moviesAdapter.addAll(movies);
        Log.d(TAG, "Load Finished");
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Movie>> loader) {
        Log.d(TAG, "Loader Reset");
        moviesAdapter.addAll(new ArrayList<Movie>());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (!mMovies.isEmpty()) {
            outState.putParcelableArrayList("movies", mMovies);
        }
        super.onSaveInstanceState(outState);
    }

    private boolean checkInternet() {
        NetworkInfo networkInfo = null;
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return networkInfo != null;
    }

    private void showInternetAttention() {
        retryView.setVisibility(View.VISIBLE);
    }

    private View.OnClickListener retryClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (checkInternet()) {
                retryView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                getSupportLoaderManager().initLoader(0, null, MainActivity.this);
            } else {
                progressBar.setVisibility(View.GONE);
                showInternetAttention();
            }
        }
    };
}
