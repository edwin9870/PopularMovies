package com.edwin.android.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.edwin.android.popularmovies.data.PopularMoviesContract.FavoriteMovieEntry;

/**
 * Created by Edwin Ramirez Ventur on 5/7/2017.
 */

public class PopularMoviesDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "popular_movies.db";
    public static final String TAG = PopularMoviesDbHelper.class.getSimpleName();

    public PopularMoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_POPULAR_MOVIES_TABLE = "CREATE TABLE " +
                FavoriteMovieEntry.TABLE_NAME + " (" +
                FavoriteMovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FavoriteMovieEntry.COLUMN_NAME_MOVIE_ID + " INTEGER NOT NULL UNIQUE," +
                FavoriteMovieEntry.COLUMN_NAME_TITLE + " VARCHAR NOT NULL," +
                FavoriteMovieEntry.COLUMN_TIMESTAMP +  " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";

        Log.d(TAG, "recreating database. SQL: " + SQL_CREATE_POPULAR_MOVIES_TABLE);
        db.execSQL(SQL_CREATE_POPULAR_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavoriteMovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
