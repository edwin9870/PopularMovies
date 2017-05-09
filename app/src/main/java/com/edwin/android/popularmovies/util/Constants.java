package com.edwin.android.popularmovies.util;

import com.edwin.android.popularmovies.BuildConfig;

/**
 * Created by Edwin Ramirez Ventur on 3/18/2017.
 */

public final class Constants {

    public final static String THE_MOVIE_DB_API_KEY = BuildConfig.THE_MOVIE_DB_API_KEY;

    public enum MovieType {
        TOP_RATED,
        POPULAR
    }


    public static class Intent {
        public static final String MOVIE = "MOVIE";
    }

    public static class KEYS {
        public static final String MOVIE_POSTER_TYPE = "MOVIE_POSTER_TYPE";
        public static final String SELECTED_MOVIE = "SELECTED_MOVIE";
        public static final String MOVIE_ID = "MOVIE_ID";
    }

    public static class LOADER_ID {
        public static final int MOVIE_POSTER_FINDER_LOADER = 777;
        public static final int MOVIE_REVIEW_LOADER = 151515;
        public static final int MOVIE_TRAILER_LOADER = 554541;
        public static final int FAVORITE_MOVIE_LOADER = 54517;
    }

    public static class LABELS {
        public static final String SYNOPSIS = "Synopsis";
        public static final String REVIEW = "Review";
        public static final String TRAILER = "Trailer";
    }

    public static class MOVIE_API {
        public static final String THEMOVIEDB_IMG_BASE_URL = "http://image.tmdb.org";
        public static final String POSTER_SIZE = "w185";
        public static final String BACKDROP_SIZE = "w342";
        public static final String THEMOVIEDB_DEV_BASE_URL = "https://api.themoviedb.org/";
    }
}
