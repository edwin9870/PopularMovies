package com.edwin.android.popularmovies.util;

import android.net.Uri;
import android.util.Log;

import com.edwin.android.popularmovies.entity.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Edwin Ramirez Ventura on 3/18/2017.
 */

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String THEMOVIEDB_IMG_BASE_URL = "http://image.tmdb.org";
    private static final String SIZE = "w185";

    private static final String THEMOVIEDB_DEV_BASE_URL = "https://api.themoviedb.org/";
    private static final String PARAM_API_KEY = "api_key";
    private static final String PARAM_PAGE = "page";
    private static final String PARAM_FIRST_PAGE = "1";




    public static List<Movie> getPopularMovies() {
        Uri builtUri = Uri.parse(THEMOVIEDB_DEV_BASE_URL).buildUpon().path("3/movie/popular")
                .appendQueryParameter(PARAM_API_KEY, Constants.THE_MOVIE_DB_API_KEY)
                .appendQueryParameter(PARAM_PAGE, PARAM_FIRST_PAGE).build();
        Log.d(TAG, "URL for finding popular movies: " + builtUri.toString());

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return getMovies(url);
    }

    public static List<Movie> getTopRatedMovies() {
        Uri builtUri = Uri.parse(THEMOVIEDB_DEV_BASE_URL).buildUpon().path("3/movie/top_rated")
                .appendQueryParameter(PARAM_API_KEY, Constants.THE_MOVIE_DB_API_KEY)
                .appendQueryParameter(PARAM_PAGE, PARAM_FIRST_PAGE).build();
        Log.d(TAG, "URL for finding top rated movies: " + builtUri.toString());

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return getMovies(url);
    }

    private static List<Movie> getMovies(URL url) {
        List<Movie> urls = new ArrayList<>();

        try {
            String pageResponse = getResponseFromHttpUrl(url);

            JSONObject jsonObj = new JSONObject(pageResponse);
            JSONArray results = jsonObj.getJSONArray("results");
            Movie movie;
            for (int i = 0; i < results.length(); i++) {
                String imagePath = results.getJSONObject(i).getString("poster_path");
                String imageUrl = THEMOVIEDB_IMG_BASE_URL + "/t/p/" + SIZE + "/" + imagePath;

                movie = new Movie();
                movie.setImagePosterUrl(imageUrl);
                movie.setTitle(results.getJSONObject(i).getString("original_title"));
                movie.setSynopsis(results.getJSONObject(i).getString("overview"));
                movie.setUserRating(results.getJSONObject(i).getDouble("vote_average"));

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    movie.setReleaseDate(df.parse(results.getJSONObject(i).getString("release_date")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                urls.add(movie);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return urls;
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
