package com.edwin.android.popularmovies.loaders;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.edwin.android.popularmovies.adapters.MoviePosterAdapter;
import com.edwin.android.popularmovies.adapters.MovieReviewAdapter;
import com.edwin.android.popularmovies.entity.Movie;
import com.edwin.android.popularmovies.entity.MovieReview;
import com.edwin.android.popularmovies.util.Constants;

import java.util.List;

/**
 * Created by Edwin Ramirez Ventur on 5/4/2017.
 */

public class FetchMovieReviewLoaderCallback implements LoaderManager.LoaderCallbacks<List<MovieReview>> {


    private Context mContext;
    private MovieReviewAdapter mMovieReviewAdapter;

    public static final String TAG = FetchMovieReviewLoaderCallback.class.getSimpleName();

    public FetchMovieReviewLoaderCallback(Context mContext, MovieReviewAdapter mMovieReviewAdapter) {
        this.mContext = mContext;
        this.mMovieReviewAdapter = mMovieReviewAdapter;
    }


    @Override
    public Loader<List<MovieReview>> onCreateLoader(int id, Bundle bundle) {
        Long movieId = (Long) bundle.getSerializable(Constants.KEYS.MOVIE_ID);
        return new FetchMovieReviewLoader(mContext, movieId);
    }

    @Override
    public void onLoadFinished(Loader<List<MovieReview>> loader, List<MovieReview> reviews) {
        Log.d(TAG, "Total reviews: " + reviews.size());
        mMovieReviewAdapter.setmReviews(reviews);
    }

    @Override
    public void onLoaderReset(Loader<List<MovieReview>> loader) {

    }
}
