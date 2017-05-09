package com.edwin.android.popularmovies.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edwin.android.popularmovies.R;
import com.edwin.android.popularmovies.activities.MainActivity;
import com.edwin.android.popularmovies.adapters.MovieTrailerAdapter;
import com.edwin.android.popularmovies.entity.Movie;
import com.edwin.android.popularmovies.entity.MovieTrailer;
import com.edwin.android.popularmovies.listeners.MovieTrailerAdapterClickHandler;
import com.edwin.android.popularmovies.loaders.FetchMovieTrailerLoaderCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.edwin.android.popularmovies.util.Constants.KEYS.MOVIE_ID;
import static com.edwin.android.popularmovies.util.Constants.LOADER_ID.MOVIE_TRAILER_LOADER;

/**
 * Created by Edwin Ramirez Ventur on 4/26/2017.
 */

public class MovieTrailerFragment extends Fragment implements MovieTrailerAdapterClickHandler {

    @BindView(R.id.recycler_view_movie_trailer)
    RecyclerView mRecyclerView;
    private MovieTrailerAdapter mMovieTrailerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_trailer_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        int columnNumber = getResources().getInteger(R.integer.trailer_column);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), columnNumber);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(false);
        mMovieTrailerAdapter = new MovieTrailerAdapter(getActivity(), this);
        mRecyclerView.setAdapter(mMovieTrailerAdapter);


        long movieId = this.getArguments().getLong(MOVIE_ID);
        setupLoader(movieId);

        super.onActivityCreated(savedInstanceState);
    }

    private void setupLoader(long movieId) {
        Bundle bundle = new Bundle();
        bundle.putLong(MOVIE_ID, movieId);

        LoaderManager loaderManager = getActivity().getSupportLoaderManager();
        Loader<List<Movie>> loader = loaderManager.getLoader(MOVIE_TRAILER_LOADER);
        FetchMovieTrailerLoaderCallback loaderCallback = new FetchMovieTrailerLoaderCallback(getActivity(), mMovieTrailerAdapter);

        if (loader == null) {
            loaderManager.initLoader(MOVIE_TRAILER_LOADER, bundle, loaderCallback);
        } else {
            loaderManager.restartLoader(MOVIE_TRAILER_LOADER, bundle, loaderCallback);
        }
    }

    @Override
    public void onClick(MovieTrailer movieTrailer) {
        Log.d(MovieTrailerFragment.class.getSimpleName(), "Movie trailer clicked, movie trailer:" + movieTrailer);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.youtube.com/watch?v="+movieTrailer.getKey()));
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
