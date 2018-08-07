package com.test.android.moviespreview;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private String title;
    private String voteAverage;
    private String voteCount;
    private String posterPath;

    public Movie(String title, String voteAverage, String voteCount, String posterPath) {
        this.title = title;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.posterPath = posterPath;
    }

    public Movie(Parcel in) {
        title = in.readString();
        voteAverage = in.readString();
        voteCount = in.readString();
        posterPath = in.readString();
    }

    @Override
    public String toString() {
        return title + ": " + voteAverage + ": " + voteCount + ": " + posterPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(voteAverage);
        parcel.writeString(voteCount);
        parcel.writeString(posterPath);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public String getPosterPath() {
        return posterPath;
    }
}
