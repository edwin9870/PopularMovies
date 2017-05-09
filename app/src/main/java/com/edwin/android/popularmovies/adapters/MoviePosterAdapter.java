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

import java.net.MalformedURLException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.edwin.android.popularmovies.util.MovieApiUtil.getPosterURL;

/**
 * Created by Edwin Ramirez Ventura on 3/18/2017.
 */

public class MoviePosterAdapter extends RecyclerView.Adapter<MoviePosterAdapter.MoviePosterAdapterViewHolder> {

    private static final String TAG = MoviePosterAdapter.class.getSimpleName();
    private List<Movie> mMovies;
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

        @BindView(R.id.image_movie_poster)
        ImageView mMoviePosterImageView;

        MoviePosterAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie movie = mMovies.get(adapterPosition);
            mClickHandler.onClick(movie);
        }
    }

    @Override
    public MoviePosterAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int idLayoutForMovieItem = R.layout.item_movie_list;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(idLayoutForMovieItem, viewGroup, false);
        return new MoviePosterAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviePosterAdapterViewHolder holder, int position) {
        String imageURL = null;
        try {
            imageURL = getPosterURL(mMovies.get(position).getImagePosterUrl()).toString();
            Log.d(TAG, "imageURL: "+ imageURL);
            Picasso picasso = Picasso.with(mContext);
            picasso.load(imageURL).fit()
                    .into(holder.mMoviePosterImageView);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(TAG, e.getStackTrace().toString());
        }

    }

    @Override
    public int getItemCount() {
        if(null == mMovies) {
            return 0;
        }

        Log.d(TAG, "Movies size:" + mMovies.size());
        return mMovies.size();
    }

    public void setmMovies(List<Movie> mMovies) {
        this.mMovies = mMovies;
        notifyDataSetChanged();
    }

}
