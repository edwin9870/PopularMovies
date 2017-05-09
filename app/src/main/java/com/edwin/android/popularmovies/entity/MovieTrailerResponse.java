package com.edwin.android.popularmovies.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Edwin Ramirez Ventur on 5/8/2017.
 */

public class MovieTrailerResponse {

    @SerializedName("results")
    private List<MovieTrailer> movieTrailers;

    public List<MovieTrailer> getMovieTrailers() {
        return movieTrailers;
    }

    public void setMovieTrailers(List<MovieTrailer> movieTrailers) {
        this.movieTrailers = movieTrailers;
    }

    @Override
    public String toString() {
        return "MovieTrailerResponse{" +
                "movieTrailers=" + movieTrailers +
                '}';
    }
}
