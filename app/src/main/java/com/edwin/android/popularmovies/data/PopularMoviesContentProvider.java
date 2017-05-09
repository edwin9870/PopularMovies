package com.edwin.android.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.edwin.android.popularmovies.data.PopularMoviesContract.FavoriteMovieEntry
        .TABLE_NAME;

/**
 * Created by Edwin Ramirez Ventur on 5/8/2017.
 */

public class PopularMoviesContentProvider extends ContentProvider {

    public static final int FAVORITE_MOVIES = 100;
    public static final int FAVORITE_MOVIE_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private PopularMoviesDbHelper mPopularMoviesDbHelper;

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(PopularMoviesContract.AUTHORITY, PopularMoviesContract
                .PATH_FAVORITE_MOVIES, FAVORITE_MOVIES);
        uriMatcher.addURI(PopularMoviesContract.AUTHORITY, PopularMoviesContract
                .PATH_FAVORITE_MOVIES + "/#", FAVORITE_MOVIE_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mPopularMoviesDbHelper = new PopularMoviesDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String
            selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = mPopularMoviesDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);

        Cursor refCursor;
        switch (match) {
            case FAVORITE_MOVIES:
                refCursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null,
                        null, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        refCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return refCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = mPopularMoviesDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        Uri uriToReturn;
        switch (match) {
            case FAVORITE_MOVIES:
                long id = db.insertWithOnConflict(PopularMoviesContract.FavoriteMovieEntry
                                .TABLE_NAME, null,
                        values, SQLiteDatabase.CONFLICT_IGNORE);

                uriToReturn = ContentUris.withAppendedId(PopularMoviesContract
                        .FavoriteMovieEntry.CONTENT_URI, id);

                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return uriToReturn;

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[]
            selectionArgs) {
        SQLiteDatabase db = mPopularMoviesDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        int tasksDeleted;

        switch (match) {
            case FAVORITE_MOVIE_WITH_ID:
                String id = uri.getPathSegments().get(1);
                tasksDeleted = db.delete(TABLE_NAME, "_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (tasksDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return tasksDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String
            selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
