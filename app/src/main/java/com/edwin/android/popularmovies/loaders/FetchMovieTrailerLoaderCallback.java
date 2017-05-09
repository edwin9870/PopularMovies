package com.edwin.android.popularmovies.loaders;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.edwin.android.popularmovies.adapters.MovieTrailerAdapter;
import com.edwin.android.popularmovies.entity.MovieTrailer;
import com.edwin.android.popularmovies.util.Constants;

import java.util.List;

/**
 * Created by Edwin Ramirez Ventur on 5/4/2017.
 */

public class FetchMovieTrailerLoaderCallback implements LoaderManager.LoaderCallbacks<List<MovieTrailer>> {

    private Context mContext;
    private MovieTrailerAdapter mMovieTrailerAdapter;

    public static final String TAG = FetchMovieTrailerLoaderCallback.class.getSimpleName();

    public FetchMovieTrailerLoaderCallback(Context mContext,
                                           MovieTrailerAdapter movieTrailerAdapter) {

        this.mContext = mContext;
        this.mMovieTrailerAdapter = movieTrailerAdapter;
    }


    @Override
    public Loader<List<MovieTrailer>> onCreateLoader(int id, Bundle bundle) {
        Long movieId = (Long) bundle.getSerializable(Constants.KEYS.MOVIE_ID);
        return new FetchMovieTrailerLoader(mContext, movieId);
    }

    @Override
    public void onLoadFinished(Loader<List<MovieTrailer>> loader, List<MovieTrailer> movieTrailers) {
        Log.d(TAG, "Total trailers: " + movieTrailers.size());
        mMovieTrailerAdapter.setMovieTrailer(movieTrailers);
    }

    @Override
    public void onLoaderReset(Loader<List<MovieTrailer>> loader) {

    }
}
