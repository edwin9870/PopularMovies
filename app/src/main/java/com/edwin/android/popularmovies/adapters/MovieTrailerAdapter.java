package com.edwin.android.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.edwin.android.popularmovies.R;
import com.edwin.android.popularmovies.entity.Movie;
import com.edwin.android.popularmovies.entity.MovieTrailer;
import com.edwin.android.popularmovies.listeners.MovieTrailerAdapterClickHandler;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Edwin Ramirez Ventura on 3/18/2017.
 */

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.MovieTrailerAdapterViewHolder> {

    private static final String TAG = MovieTrailerAdapter.class.getSimpleName();
    private List<MovieTrailer> mMovieTrailer;
    private Context mContext;
    private MovieTrailerAdapterClickHandler mClickHandler;

    public MovieTrailerAdapter(Context mContext, MovieTrailerAdapterClickHandler mClickHandler) {
        this.mContext = mContext;
        this.mClickHandler = mClickHandler;
    }

    class MovieTrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.image_movie_thumbnail)
        ImageView mTrailerThumbnail;
        @BindView(R.id.text_trailer_name)
        TextView mtrailerNameTextView;

        MovieTrailerAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mTrailerThumbnail.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "Movie trailer clicked");
            int adapterPosition = getAdapterPosition();
            MovieTrailer movieTrailer = mMovieTrailer.get(adapterPosition);
            mClickHandler.onClick(movieTrailer);
        }
    }

    @Override
    public MovieTrailerAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int idLayoutForMovieItem = R.layout.item_movie_trailer;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(idLayoutForMovieItem, viewGroup, false);
        return new MovieTrailerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieTrailerAdapterViewHolder holder, int position) {
        MovieTrailer movieTrailer = mMovieTrailer.get(position);

        holder.mtrailerNameTextView.setText(movieTrailer.getName());
        String imageURL = "http://img.youtube.com/vi/"+movieTrailer.getKey()+"/0.jpg";

        Log.d(TAG, "imageURL: "+ imageURL);
        Picasso picasso = Picasso.with(mContext);
        picasso.load(imageURL).fit()
                .into(holder.mTrailerThumbnail);
    }

    @Override
    public int getItemCount() {
        if (null == mMovieTrailer) {
            return 0;
        }

        Log.d(TAG, "Trailer size:" + mMovieTrailer.size());
        return mMovieTrailer.size();
    }

    public void setMovieTrailer(List<MovieTrailer> mMovieTrailer) {
        this.mMovieTrailer = mMovieTrailer;
        notifyDataSetChanged();
    }

}
