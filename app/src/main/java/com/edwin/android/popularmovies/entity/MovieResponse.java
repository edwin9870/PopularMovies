package com.edwin.android.popularmovies.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Edwin Ramirez Ventur on 5/8/2017.
 */

public class MovieResponse {

    @SerializedName("results")
    private List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return "MovieResponse{" +
                "movies=" + movies +
                '}';
    }
}
