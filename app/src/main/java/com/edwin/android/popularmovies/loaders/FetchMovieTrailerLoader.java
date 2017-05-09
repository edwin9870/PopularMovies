package com.edwin.android.popularmovies.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.edwin.android.popularmovies.entity.MovieTrailer;
import com.edwin.android.popularmovies.rest.MovieAPI;
import com.edwin.android.popularmovies.rest.MovieAppClient;
import com.edwin.android.popularmovies.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edwin Ramirez Ventur on 5/4/2017.
 */

public class FetchMovieTrailerLoader extends AsyncTaskLoader<List<MovieTrailer>> {

    private List<MovieTrailer> mMovieTrailers;
    private long mMovieId;

    public FetchMovieTrailerLoader(Context context, long movieId) {
        super(context);
        this.mMovieId = movieId;
    }

    @Override
    protected void onStartLoading() {
        if (mMovieTrailers != null) {
            deliverResult(mMovieTrailers);
        } else {
            forceLoad();
        }
    }

    @Override
    public List<MovieTrailer> loadInBackground() {
        try {
            MovieAPI movieAPI = MovieAppClient.getClient().create(MovieAPI.class);
            List<MovieTrailer> movieTrailers = movieAPI.getMovieTrailers(mMovieId, Constants
                    .THE_MOVIE_DB_API_KEY).execute().body().getMovieTrailers();
            return movieTrailers;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void deliverResult(List<MovieTrailer> trailers) {
        mMovieTrailers = trailers;
        super.deliverResult(trailers);
    }
}
