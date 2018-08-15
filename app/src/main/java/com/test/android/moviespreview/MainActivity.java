package com.test.android.moviespreview;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();

    private ArrayList<Result> results;
    private MoviesAdapter moviesAdapter;

    private static MoviesDBAPI moviesDBAPI;
    private ModelMovies moviesResult;


    @BindView(R.id.retry_view)
    View retryView;
    @BindView(R.id.retry_btn)
    Button retryBtn;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.grid_view)
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        moviesDBAPI = App.getApi();
        moviesAdapter = new MoviesAdapter(this, new ArrayList<Result>());
        gridView.setAdapter(moviesAdapter);
        results = new ArrayList<>();

        if (savedInstanceState == null || !savedInstanceState.containsKey("result")) {
            if (checkInternet()) {
                moviesDBAPI.discoverMovie(Constants.API_KEY, Constants.POPULARITY).enqueue(new Callback<ModelMovies>() {
                    @Override
                    public void onResponse(Call<ModelMovies> call, Response<ModelMovies> response) {
                        moviesResult = response.body();
                        Log.d(TAG, "RESPONSE " + response.toString());
                        results.addAll(moviesResult.getResults());
                        moviesAdapter.addAll(results);
                        Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<ModelMovies> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                });
                Log.d(TAG, "Start load movies");
            } else {
                progressBar.setVisibility(View.GONE);
                showInternetAttention();
            }
        } else {
            Log.d(TAG, "Restoring movies array");
            results = savedInstanceState.getParcelableArrayList("result");
            progressBar.setVisibility(View.GONE);
            moviesAdapter.clear();
            moviesAdapter.addAll(results);
        }
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (!results.isEmpty()) {
            outState.putParcelableArrayList("result", results);
        }
        super.onSaveInstanceState(outState);

    }

    @OnClick(R.id.retry_btn)
    void retryClick(View view) {
        if (checkInternet()) {
            retryView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
            showInternetAttention();
        }
    }
}
