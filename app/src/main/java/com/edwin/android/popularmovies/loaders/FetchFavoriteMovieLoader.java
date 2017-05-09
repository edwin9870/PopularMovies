package com.edwin.android.popularmovies.loaders;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.edwin.android.popularmovies.data.PopularMoviesContract;

import static com.edwin.android.popularmovies.data.PopularMoviesContract.FavoriteMovieEntry
        .COLUMN_TIMESTAMP;

/**
 * Created by Edwin Ramirez Ventur on 5/4/2017.
 */

public class FetchFavoriteMovieLoader extends AsyncTaskLoader<Cursor> {

    public static final String TAG = FetchFavoriteMovieLoader.class.getSimpleName();
    private Cursor mCursor;
    private SQLiteDatabase mSqLiteDatabase;

    public FetchFavoriteMovieLoader(Context context, SQLiteDatabase mSqLiteDatabase) {
        super(context);
        this.mSqLiteDatabase = mSqLiteDatabase;
    }


    @Override
    protected void onStartLoading() {
        Log.d(TAG, "mCursor value: " + mCursor);
        if (mCursor != null) {
            deliverResult(mCursor);
        } else {
            forceLoad();
        }
    }

    @Override
    public Cursor loadInBackground() {
        try {

            Cursor refCursor = getContext().getContentResolver().query(PopularMoviesContract
                            .FavoriteMovieEntry.CONTENT_URI,
                    null,
                    null,
                    null,
                    COLUMN_TIMESTAMP);
            return refCursor;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deliverResult(Cursor cursor) {
        mCursor = cursor;
        super.deliverResult(mCursor);
    }
}
