package com.edwin.android.popularmovies.loaders;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.edwin.android.popularmovies.adapters.FavoriteMovieAdapter;
import com.edwin.android.popularmovies.adapters.MovieReviewAdapter;
import com.edwin.android.popularmovies.entity.MovieReview;
import com.edwin.android.popularmovies.util.Constants;

import java.util.List;

/**
 * Created by Edwin Ramirez Ventur on 5/4/2017.
 */

public class FetchFavoriteMovieLoaderCallback implements LoaderManager.LoaderCallbacks<Cursor> {


    private Context mContext;
    private FavoriteMovieAdapter mFavoriteMovieAdapter;
    private RecyclerView mRecyclerView;
    private ProgressBar mLoadingIndicator;
    private SQLiteDatabase mSqLiteDatabase;

    public static final String TAG = FetchFavoriteMovieLoaderCallback.class.getSimpleName();

    public FetchFavoriteMovieLoaderCallback(Context mContext, FavoriteMovieAdapter
            mFavoriteMovieAdapter, RecyclerView mRecyclerView, ProgressBar mLoadingIndicator,
                                            SQLiteDatabase mSqLiteDatabase) {
        this.mContext = mContext;
        this.mFavoriteMovieAdapter = mFavoriteMovieAdapter;
        this.mRecyclerView = mRecyclerView;
        this.mLoadingIndicator = mLoadingIndicator;
        this.mSqLiteDatabase = mSqLiteDatabase;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        mLoadingIndicator.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
        return new FetchFavoriteMovieLoader(mContext, mSqLiteDatabase);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.d(TAG, "Total favorite movies: " + cursor.getCount());
        mFavoriteMovieAdapter.swapCursor(cursor);
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
