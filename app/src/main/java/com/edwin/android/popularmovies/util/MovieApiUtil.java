package com.edwin.android.popularmovies.util;

import java.net.MalformedURLException;
import java.net.URL;

import static com.edwin.android.popularmovies.util.Constants.MOVIE_API.BACKDROP_SIZE;
import static com.edwin.android.popularmovies.util.Constants.MOVIE_API.POSTER_SIZE;
import static com.edwin.android.popularmovies.util.Constants.MOVIE_API.THEMOVIEDB_IMG_BASE_URL;

/**
 * Created by Edwin Ramirez Ventur on 5/8/2017.
 */

public final class MovieApiUtil {

    private MovieApiUtil() {}

    public static URL getPosterURL(String posterImagePath) throws MalformedURLException {
        URL url = new URL(THEMOVIEDB_IMG_BASE_URL + "/t/p/" + POSTER_SIZE + "/" +
                posterImagePath);
        return url;
    }

    public static URL getBackdropUrl(String posterImagePath) throws MalformedURLException {
        URL url = new URL(THEMOVIEDB_IMG_BASE_URL + "/t/p/" + BACKDROP_SIZE + "/" +
                posterImagePath);
        return url;
    }
}
