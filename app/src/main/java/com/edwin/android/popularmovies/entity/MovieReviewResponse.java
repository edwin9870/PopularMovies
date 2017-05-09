package com.edwin.android.popularmovies.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Edwin Ramirez Ventur on 5/8/2017.
 */

public class MovieReviewResponse {

    @SerializedName("results")
    private List<MovieReview> movieReviews;

    public List<MovieReview> getMovieReviews() {
        return movieReviews;
    }

    public void setMovieReviews(List<MovieReview> movieReviews) {
        this.movieReviews = movieReviews;
    }

    @Override
    public String toString() {
        return "MovieReviewResponse{" +
                "movieReviews=" + movieReviews +
                '}';
    }
}
