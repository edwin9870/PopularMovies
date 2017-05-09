package com.edwin.android.popularmovies.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.edwin.android.popularmovies.entity.MovieReview;
import com.edwin.android.popularmovies.rest.MovieAPI;
import com.edwin.android.popularmovies.rest.MovieAppClient;
import com.edwin.android.popularmovies.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edwin Ramirez Ventur on 5/4/2017.
 */

public class FetchMovieReviewLoader extends AsyncTaskLoader<List<MovieReview>> {

    private List<MovieReview> mMovieReview;
    private long mMovieId;

    public FetchMovieReviewLoader(Context context, long movieId) {
        super(context);
        this.mMovieId = movieId;
    }


    @Override
    protected void onStartLoading() {
        if (mMovieReview != null) {
            deliverResult(mMovieReview);
        } else {
            forceLoad();
        }
    }

    @Override
    public List<MovieReview> loadInBackground() {
        try {
            MovieAPI movieAPI = MovieAppClient.getClient().create(MovieAPI.class);
            List<MovieReview> movieReviews = movieAPI.getMovieReviews(mMovieId, Constants
                    .THE_MOVIE_DB_API_KEY).execute().body().getMovieReviews();
            return movieReviews;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void deliverResult(List<MovieReview> reviews) {
        mMovieReview = reviews;
        super.deliverResult(reviews);
    }
}
