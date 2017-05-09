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
import com.edwin.android.popularmovies.entity.Movie;
import com.edwin.android.popularmovies.util.Constants.MovieType;

import java.util.List;

import static com.edwin.android.popularmovies.util.Constants.KEYS.MOVIE_POSTER_TYPE;

/**
 * Created by Edwin Ramirez Ventur on 5/4/2017.
 */

public class FetchMoviePosterLoaderCallback implements LoaderManager.LoaderCallbacks<List<Movie>> {

    private Context mContext;
    private MoviePosterAdapter mMoviePosterAdapter;
    private ProgressBar mLoadingIndicator;
    private RecyclerView mRecyclerView;

    public static final String TAG = FetchMoviePosterLoaderCallback.class.getSimpleName();

    public FetchMoviePosterLoaderCallback(Context mContext,
                                          MoviePosterAdapter mMoviePosterAdapter, ProgressBar
                                                  mLoadingIndicator, RecyclerView mRecyclerView) {

        this.mContext = mContext;
        this.mMoviePosterAdapter = mMoviePosterAdapter;
        this.mLoadingIndicator = mLoadingIndicator;
        this.mRecyclerView = mRecyclerView;
    }


    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle bundle) {
        MovieType movieType = (MovieType) bundle.getSerializable(MOVIE_POSTER_TYPE);
        mLoadingIndicator.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
        return new FetchMoviePosterLoader(mContext, movieType);
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {
        Log.d(TAG, "Total movies: " + movies.size());
        mMoviePosterAdapter.setmMovies(movies);
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {

    }
}
