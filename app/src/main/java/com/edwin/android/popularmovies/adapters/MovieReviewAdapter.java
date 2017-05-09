package com.edwin.android.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edwin.android.popularmovies.R;
import com.edwin.android.popularmovies.entity.MovieReview;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Edwin Ramirez Ventura on 3/18/2017.
 */

public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.MovieReviewAdapterViewHolder> {

    private static final String TAG = MovieReviewAdapter.class.getSimpleName();
    private List<MovieReview> mReviews;

    class MovieReviewAdapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_movie_review_author)
        TextView mMovieReviewAuthorTextView;
        @BindView(R.id.text_movie_review)
        TextView mMovieReviewTextView;

        MovieReviewAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public MovieReviewAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int idLayoutForMovieItem = R.layout.item_movie_review;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(idLayoutForMovieItem, viewGroup, false);
        return new MovieReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieReviewAdapterViewHolder holder, int position) {
        MovieReview review = mReviews.get(position);
        holder.mMovieReviewAuthorTextView.setText(review.getAuthor());
        holder.mMovieReviewTextView.setText(review.getReview());

        Log.d(TAG, "Review author: " + review.getAuthor());
    }

    @Override
    public int getItemCount() {
        if (null == mReviews) {
            return 0;
        }

        Log.d(TAG, "Reviews size:" + mReviews.size());
        return mReviews.size();
    }

    public void setmReviews(List<MovieReview> mReviews) {
        this.mReviews = mReviews;
        notifyDataSetChanged();
    }

}
