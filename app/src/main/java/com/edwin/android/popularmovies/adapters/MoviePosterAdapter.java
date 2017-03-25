package com.edwin.android.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.edwin.android.popularmovies.R;
import com.edwin.android.popularmovies.entity.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Edwin Ramirez Ventura on 3/18/2017.
 */

public class MoviePosterAdapter extends RecyclerView.Adapter<MoviePosterAdapter.MoviePosterAdapterViewHolder> {

    private static final String TAG = MoviePosterAdapter.class.getSimpleName();
    private List<Movie> movies;
    private Context mContext;
    private MoviePosterAdapterOnClickHandler mClickHandler;

    public MoviePosterAdapter(Context context, MoviePosterAdapterOnClickHandler clickHandler) {
        this.mContext = context;
        this.mClickHandler = clickHandler;
    }

    public interface MoviePosterAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    class MoviePosterAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mMoviePosterImageView;
        MoviePosterAdapterViewHolder(View itemView) {
            super(itemView);
            mMoviePosterImageView = (ImageView) itemView.findViewById(R.id.iv_movie_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie movie = movies.get(adapterPosition);
            mClickHandler.onClick(movie);
        }
    }

    @Override
    public MoviePosterAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int idLayoutForMovieItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(idLayoutForMovieItem, viewGroup, false);
        return new MoviePosterAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviePosterAdapterViewHolder holder, int position) {
        String imageURL = movies.get(position).getImagePosterUrl();

        Log.d(TAG, "imageURL: "+ imageURL);
        Picasso picasso = Picasso.with(mContext);
        picasso.load(imageURL).fit()
                .into(holder.mMoviePosterImageView);
    }

    @Override
    public int getItemCount() {
        if(null == movies) {
            return 0;
        }

        Log.d(TAG, "Movies size:" + movies.size());
        return movies.size();
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

}
