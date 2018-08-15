package com.test.android.moviespreview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoviesAdapter extends ArrayAdapter<Result> {
    private static final String POSTER_ROOT_URL = "https://image.tmdb.org/t/p/w500";

    public MoviesAdapter(Context context, ArrayList<Result> movies) {
        super(context, 0, movies);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View itemView = view;
        if (view == null) {
            itemView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_movie, viewGroup, false);
        }
        Result movie = getItem(position);

        TextView title = itemView.findViewById(R.id.title);
        TextView score = itemView.findViewById(R.id.score);
        ImageView poster = itemView.findViewById(R.id.poster);
        title.setText(movie.getTitle());
        String scoreText = movie.getVoteAverage() + " (" + movie.getVoteCount() + ")";
        score.setText(scoreText);
        String posterUrl = POSTER_ROOT_URL + movie.getPosterPath();
        Picasso.get()
                .load(posterUrl)
                .into(poster);

        return itemView;
    }
}
