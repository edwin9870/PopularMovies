package com.edwin.android.popularmovies.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.edwin.android.popularmovies.R;
import com.edwin.android.popularmovies.adapters.FavoriteMovieAdapter;
import com.edwin.android.popularmovies.data.PopularMoviesContract;
import com.edwin.android.popularmovies.data.PopularMoviesDbHelper;
import com.edwin.android.popularmovies.loaders.FetchFavoriteMovieLoaderCallback;
import com.edwin.android.popularmovies.util.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteMovieActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view_favorite_movie)
    RecyclerView mRecyclerView;
    @BindView(R.id.progress_bar_loading_indicator)
    ProgressBar mLoadingIndicator;
    @BindView(R.id.toolbar_main)
    Toolbar mToolbar;

    private FavoriteMovieAdapter mFavoriteMovieAdapter;
    public SQLiteDatabase mDb;

    private static final String TAG = FavoriteMovieActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movie);
        ButterKnife.bind(this);

        mDb = new PopularMoviesDbHelper(this).getWritableDatabase();

        setSupportActionBar(mToolbar);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager
                .VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(false);

        mFavoriteMovieAdapter = new FavoriteMovieAdapter();
        Log.i(TAG, "Calling Favorite movie adapter");
        mRecyclerView.setAdapter(mFavoriteMovieAdapter);

        setupTouchHelper();

        setupLoader(false);

    }

    private void setupLoader(boolean restart) {
        LoaderManager loaderManager = getSupportLoaderManager();
        FetchFavoriteMovieLoaderCallback loaderCallback = new FetchFavoriteMovieLoaderCallback(this,
                mFavoriteMovieAdapter, mRecyclerView, mLoadingIndicator, mDb);

        if(restart) {
            loaderManager.restartLoader(Constants.LOADER_ID.FAVORITE_MOVIE_LOADER, null, loaderCallback);
        }else {
            loaderManager.initLoader(Constants.LOADER_ID.FAVORITE_MOVIE_LOADER, null, loaderCallback);
        }

    }

    private void setupTouchHelper() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT |
                ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                long id = (long) viewHolder.itemView.getTag();

                Uri uri = PopularMoviesContract.FavoriteMovieEntry.CONTENT_URI;
                uri = uri.buildUpon().appendPath(String.valueOf(id)).build();

                getContentResolver().delete(uri, null, null);

                Log.i(TAG, "Deleted movie with ID: " + id);
                Toast.makeText(FavoriteMovieActivity.this, "Movie deleted", Toast.LENGTH_SHORT).show();
                setupLoader(true);
            }

        }).attachToRecyclerView(mRecyclerView);
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
            Class destinationActivity = MainActivity.class;

            Intent intent = new Intent(this, destinationActivity);
            intent.putExtra(Constants.KEYS.MOVIE_POSTER_TYPE, Constants.MovieType.POPULAR);
            startActivity(intent);
            Toast.makeText(this, "Sorting by popular movies", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.item_top_rated) {
            Class destinationActivity = MainActivity.class;

            Intent intent = new Intent(this, destinationActivity);
            intent.putExtra(Constants.KEYS.MOVIE_POSTER_TYPE, Constants.MovieType.TOP_RATED);
            startActivity(intent);
            Toast.makeText(this, "Sorting by top rated movies", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
