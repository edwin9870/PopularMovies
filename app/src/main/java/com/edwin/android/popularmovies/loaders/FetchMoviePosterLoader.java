package com.edwin.android.popularmovies.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.edwin.android.popularmovies.entity.Movie;
import com.edwin.android.popularmovies.rest.MovieAPI;
import com.edwin.android.popularmovies.rest.MovieAppClient;
import com.edwin.android.popularmovies.util.Constants;
import com.edwin.android.popularmovies.util.Constants.MovieType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edwin Ramirez Ventur on 5/4/2017.
 */

public class FetchMoviePosterLoader extends AsyncTaskLoader<List<Movie>> {

    private List<Movie> mMovies;
    private MovieType mMoviesType;

    public FetchMoviePosterLoader(Context context, MovieType moviesType) {
        super(context);
        mMoviesType = moviesType;
    }

    @Override
    protected void onStartLoading() {
        if (mMovies != null) {
            deliverResult(mMovies);
        } else {
            forceLoad();
        }
    }

    @Override
    public List<Movie> loadInBackground() {
        try {

            List<Movie> movieList;
            MovieAPI movieAPI = MovieAppClient.getClient().create(MovieAPI.class);
            if (mMoviesType.equals(MovieType.POPULAR)) {

                movieList = movieAPI.getTopPopular(Constants.THE_MOVIE_DB_API_KEY)
                        .execute().body().getMovies();
            } else {
                movieList = movieAPI.getTopRated(Constants.THE_MOVIE_DB_API_KEY)
                        .execute().body().getMovies();
            }

            Log.d(FetchMoviePosterLoader.class.getSimpleName(), "Movie list fetched: "+ movieList);
            return movieList;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void deliverResult(List<Movie> movies) {
        mMovies = movies;
        super.deliverResult(movies);
    }
}
