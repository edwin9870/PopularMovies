package com.edwin.android.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Edwin Ramirez Ventur on 5/7/2017.
 */

public final class PopularMoviesContract {

    public static final String AUTHORITY = "com.edwin.android.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_FAVORITE_MOVIES = "favorite_movies";

    private PopularMoviesContract() {}

    public static class FavoriteMovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITE_MOVIES).build();

        public static final String TABLE_NAME = "favorite_movie";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_MOVIE_ID = "movie_id";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
