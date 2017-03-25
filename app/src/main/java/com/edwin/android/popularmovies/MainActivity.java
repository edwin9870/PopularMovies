package com.edwin.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.edwin.android.popularmovies.adapters.MoviePosterAdapter;
import com.edwin.android.popularmovies.entity.Movie;
import com.edwin.android.popularmovies.util.Constants;
import com.edwin.android.popularmovies.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MoviePosterAdapter.MoviePosterAdapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private MoviePosterAdapter mMoviePosterAdapter;
    private ProgressBar mLoadingIndicator;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movie_poster);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);

        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(false);

        mMoviePosterAdapter = new MoviePosterAdapter(this, this);
        Log.i(TAG, "Calling the adapter");
        mRecyclerView.setAdapter(mMoviePosterAdapter);

        new FetchImagesPathTask().execute(Constants.MovieSortOrder.POPULAR);
    }

    @Override
    public void onClick(Movie movie) {
        Log.d(TAG, "Clicked movie: " + movie);
        Context context = MainActivity.this;
        Class destinationActivity = MovieDetailActivity.class;

        Intent intent = new Intent(context, destinationActivity);

        intent.putExtra(Constants.Intent.MOVIE, movie);
        startActivity(intent);
    }

    private class FetchImagesPathTask extends AsyncTask<Constants.MovieSortOrder, Void, List<Movie>> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.INVISIBLE);

        }

        @Override
        protected List<Movie> doInBackground(Constants.MovieSortOrder... params) {
            try {


                List<Movie> moviesImagePath;

                if(params[0].equals(Constants.MovieSortOrder.POPULAR)) {
                    moviesImagePath = NetworkUtils.getPopularMovies();
                } else {
                    moviesImagePath = NetworkUtils.getTopRatedMovies();
                }

                return moviesImagePath;
            } catch(Exception e) {
                e.printStackTrace();
                return new ArrayList<>();
            }


        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            String TAG = FetchImagesPathTask.class.getSimpleName();
            Log.d(TAG, "Total movies: " + movies.size());
            mMoviePosterAdapter.setMovies(movies);
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie_poster, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.mn_most_popular) {
            new FetchImagesPathTask().execute(Constants.MovieSortOrder.POPULAR);
            Toast.makeText(this, "Sorting by popular movies", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.mn_top_rated) {
            new FetchImagesPathTask().execute(Constants.MovieSortOrder.TOP_RATED);
            Toast.makeText(this, "Sorting by top rated movies", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
