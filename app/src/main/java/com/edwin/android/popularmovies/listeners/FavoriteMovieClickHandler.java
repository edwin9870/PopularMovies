package com.edwin.android.popularmovies.listeners;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.edwin.android.popularmovies.data.PopularMoviesContract;
import com.edwin.android.popularmovies.entity.Movie;

/**
 * Created by Edwin Ramirez Ventur on 5/5/2017.
 */

public class FavoriteMovieClickHandler implements View.OnClickListener {

    public static final String TAG = FavoriteMovieClickHandler.class.getSimpleName();
    private Context mContext;
    private Movie mMovie;
    private SQLiteDatabase mDb;

    public FavoriteMovieClickHandler(Context mContext, Movie mMovie) {
        this.mContext = mContext;
        this.mMovie = mMovie;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "Movie for adding to favorites: " + mMovie);

        ContentValues contentValues = new ContentValues();
        contentValues.put(PopularMoviesContract.FavoriteMovieEntry.COLUMN_NAME_TITLE, mMovie.getTitle());
        contentValues.put(PopularMoviesContract.FavoriteMovieEntry.COLUMN_NAME_MOVIE_ID, mMovie.getId());

        Uri uri = mContext.getContentResolver().insert(PopularMoviesContract.FavoriteMovieEntry.CONTENT_URI, contentValues);
        Log.d(TAG, "Favorite movies uri generated: " + uri.toString());
        Toast.makeText(mContext, mMovie.getTitle() + " added to favorites", Toast.LENGTH_SHORT).show();
    }
}
