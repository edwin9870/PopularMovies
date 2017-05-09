package com.edwin.android.popularmovies.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edwin.android.popularmovies.R;
import com.edwin.android.popularmovies.entity.Movie;
import com.edwin.android.popularmovies.util.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Edwin Ramirez Ventur on 4/26/2017.
 */

public class MovieSynopsisFragment extends Fragment {

    @BindView(R.id.text_synopsis)
    TextView mSynopsisTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_synopsis, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        Log.d(MovieSynopsisFragment.class.getSimpleName(), "....Checking if savedInstance is null. savedInstanceState: "+ bundle);
        if(bundle != null) {
            Log.d(MovieSynopsisFragment.class.getSimpleName(), "savedInstance is not null");
            Movie movie = bundle.getParcelable(Constants.KEYS.SELECTED_MOVIE);
            Log.d(MovieSynopsisFragment.class.getSimpleName(), "Movie's synopsis: "+ movie.getSynopsis());
            mSynopsisTextView.setText(movie.getSynopsis());
        }
        super.onActivityCreated(savedInstanceState);
    }
}
