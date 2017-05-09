package com.edwin.android.popularmovies.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edwin.android.popularmovies.R;
import com.edwin.android.popularmovies.adapters.MovieReviewAdapter;
import com.edwin.android.popularmovies.entity.Movie;
import com.edwin.android.popularmovies.loaders.FetchMovieReviewLoaderCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.edwin.android.popularmovies.util.Constants.KEYS.MOVIE_ID;
import static com.edwin.android.popularmovies.util.Constants.LOADER_ID.MOVIE_REVIEW_LOADER;

/**
 * Created by Edwin Ramirez Ventur on 4/26/2017.
 */

public class MovieReviewFragment extends Fragment {

    @BindView(R.id.recycler_view_movie_review)
    RecyclerView mRecyclerView;
    private MovieReviewAdapter mMovieReviewAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_review, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(false);
        mMovieReviewAdapter = new MovieReviewAdapter();
        mRecyclerView.setAdapter(mMovieReviewAdapter);

        long movieId = this.getArguments().getLong(MOVIE_ID);
        setupLoader(movieId);
        super.onActivityCreated(savedInstanceState);
    }

    private void setupLoader(long movieId) {
        Bundle bundle = new Bundle();
        bundle.putLong(MOVIE_ID, movieId);

        LoaderManager loaderManager = getActivity().getSupportLoaderManager();
        FetchMovieReviewLoaderCallback loaderCallback = new FetchMovieReviewLoaderCallback(getActivity(), mMovieReviewAdapter);


        loaderManager.initLoader(MOVIE_REVIEW_LOADER, bundle, loaderCallback);

    }
}
