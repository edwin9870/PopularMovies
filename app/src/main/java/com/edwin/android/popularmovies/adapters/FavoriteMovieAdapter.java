package com.edwin.android.popularmovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edwin.android.popularmovies.R;
import com.edwin.android.popularmovies.data.PopularMoviesContract;
import com.edwin.android.popularmovies.data.PopularMoviesContract.FavoriteMovieEntry;
import com.edwin.android.popularmovies.entity.FavoriteMovie;
import com.edwin.android.popularmovies.entity.MovieReview;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Edwin Ramirez Ventura on 3/18/2017.
 */

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.MovieReviewAdapterViewHolder> {

    private static final String TAG = FavoriteMovieAdapter.class.getSimpleName();
    private Cursor mCursor;

    class MovieReviewAdapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.favorite_movie_name)
        TextView favoriteMovieNameTextView;

        MovieReviewAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public MovieReviewAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int idLayoutForMovieItem = R.layout.item_favorite_movie;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(idLayoutForMovieItem, viewGroup, false);
        return new MovieReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieReviewAdapterViewHolder holder, int position) {
        Log.d(TAG, "mCursor value: "+ mCursor);

        if (!mCursor.moveToPosition(position))
            return;
        String movieName = mCursor.getString(mCursor.getColumnIndex(FavoriteMovieEntry.COLUMN_NAME_TITLE));

        long rowId = mCursor.getLong(mCursor.getColumnIndex(FavoriteMovieEntry._ID));
        holder.favoriteMovieNameTextView.setText(movieName);
        Log.d(TAG, "Favorite Movie name: " + movieName);

        holder.itemView.setTag(rowId);
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            Log.d(TAG, "mCursor is null");
            return 0;
        }

        Log.d(TAG, "Favorites movies size:" + mCursor.getCount());
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        // Always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            Log.d(TAG,"refresh Favorites movies");
            this.notifyDataSetChanged();
        }
    }
}
