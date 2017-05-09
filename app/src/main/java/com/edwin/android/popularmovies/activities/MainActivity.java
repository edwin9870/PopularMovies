package com.edwin.android.popularmovies.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.edwin.android.popularmovies.R;
import com.edwin.android.popularmovies.adapters.MoviePosterAdapter;
import com.edwin.android.popularmovies.data.PopularMoviesDbHelper;
import com.edwin.android.popularmovies.entity.Movie;
import com.edwin.android.popularmovies.loaders.FetchMoviePosterLoaderCallback;
import com.edwin.android.popularmovies.util.Constants;
import com.edwin.android.popularmovies.util.Constants.MovieType;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.edwin.android.popularmovies.util.Constants.KEYS.MOVIE_POSTER_TYPE;
import static com.edwin.android.popularmovies.util.Constants.LOADER_ID.MOVIE_POSTER_FINDER_LOADER;
import static com.edwin.android.popularmovies.util.Constants.MovieType.POPULAR;
import static com.edwin.android.popularmovies.util.Constants.MovieType.TOP_RATED;

public class MainActivity extends AppCompatActivity implements MoviePosterAdapter
        .MoviePosterAdapterOnClickHandler {

    @BindView(R.id.recycler_view_movie_poster)
    RecyclerView mRecyclerView;
    @BindView(R.id.progress_bar_loading_indicator)
    ProgressBar mLoadingIndicator;
    @BindView(R.id.toolbar_main)
    Toolbar mToolbar;

    private MoviePosterAdapter mMoviePosterAdapter;
    public SQLiteDatabase mDb;

    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mDb = new PopularMoviesDbHelper(this).getWritableDatabase();

        setSupportActionBar(mToolbar);
        int columnNumber = getResources().getInteger(R.integer.poster_column);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, columnNumber);

        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(false);

        mMoviePosterAdapter = new MoviePosterAdapter(this, this);
        Log.i(TAG, "Calling the adapter");
        mRecyclerView.setAdapter(mMoviePosterAdapter);

        Bundle bundleExtra = this.getIntent().getExtras();
        if (bundleExtra != null && bundleExtra.containsKey(MOVIE_POSTER_TYPE)) {
            startLoader(MOVIE_POSTER_FINDER_LOADER, (MovieType) bundleExtra.getSerializable(MOVIE_POSTER_TYPE), false);
        } else {
            startLoader(MOVIE_POSTER_FINDER_LOADER, POPULAR, false);
        }
    }

    @Override
    public void onClick(Movie movie) {
        Log.d(TAG, "Clicked movie: " + movie);
        Class destinationActivity = MovieDetailActivity.class;

        Intent intent = new Intent(this, destinationActivity);

        intent.putExtra(Constants.Intent.MOVIE, movie);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item_most_popular) {
            startLoader(MOVIE_POSTER_FINDER_LOADER, POPULAR, true);
            Toast.makeText(this, "Sorting by popular movies", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.item_top_rated) {
            startLoader(MOVIE_POSTER_FINDER_LOADER, TOP_RATED, true);
            Toast.makeText(this, "Sorting by top rated movies", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.item_favorites_movie) {
            Class destinationActivity = FavoriteMovieActivity.class;

            Intent intent = new Intent(this, destinationActivity);
            startActivity(intent);
            Toast.makeText(this, "Sorting by favorites movies", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void startLoader(int loaderId, MovieType movieType, boolean restart) {
        mRecyclerView.setAdapter(mMoviePosterAdapter);
        LoaderManager loaderManager = getSupportLoaderManager();
        Bundle bundle = new Bundle();
        bundle.putSerializable(MOVIE_POSTER_TYPE, movieType);

        FetchMoviePosterLoaderCallback loaderCallback = new FetchMoviePosterLoaderCallback(this,
                mMoviePosterAdapter, mLoadingIndicator, mRecyclerView);

        if(restart) {
            loaderManager.restartLoader(loaderId, bundle, loaderCallback);
        }else {
            loaderManager.initLoader(loaderId, bundle, loaderCallback);
        }
    }

}
