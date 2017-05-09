package com.edwin.android.popularmovies.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.edwin.android.popularmovies.R;
import com.edwin.android.popularmovies.entity.Movie;
import com.edwin.android.popularmovies.fragments.MovieReviewFragment;
import com.edwin.android.popularmovies.fragments.MovieSynopsisFragment;
import com.edwin.android.popularmovies.fragments.MovieTrailerFragment;
import com.edwin.android.popularmovies.listeners.FavoriteMovieClickHandler;
import com.edwin.android.popularmovies.util.Constants;
import com.squareup.phrase.Phrase;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.edwin.android.popularmovies.util.Constants.KEYS.MOVIE_ID;
import static com.edwin.android.popularmovies.util.Constants.KEYS.SELECTED_MOVIE;
import static com.edwin.android.popularmovies.util.MovieApiUtil.getBackdropUrl;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String TAG = MovieDetailActivity.class.getSimpleName();
    public static final String RATING_PHRASE = "rating";

    @BindView(R.id.image_movie_backdrop)
    ImageView mMovieBackdropImageView;
    @BindView(R.id.text_movie_name)
    TextView mMovieNameTextView;
    @BindView(R.id.text_release_date)
    TextView mReleaseDateTextView;
    @BindView(R.id.text_user_rating)
    TextView mUserRatingTextView;
    @BindView(R.id.toolbar_detail_movie)
    Toolbar mToolbar;
    @BindView(R.id.tab_movie_detail)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager_movie_detail)
    ViewPager mViewPager;
    @BindView(R.id.floating_button_add_favorite)
    FloatingActionButton mFloatingActionButton;
    @BindView(R.id.collapsing_toolbar_movie_detail)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    private Movie mMovie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        setupBar();
        Log.d(TAG, "Movie received: " + mMovie);

        if (mMovie == null) {
            Log.d(TAG, "The movie value is null, end the activity");
            return;
        }

        Picasso picasso = Picasso.with(this);
        try {
            String backdropUrl = getBackdropUrl(mMovie.getImageBackdropUrl()).toString();
            Log.d(TAG, "Backdrop url: "+ backdropUrl);
            picasso.load(backdropUrl).fit().into(mMovieBackdropImageView);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }

        mMovieNameTextView.setText(mMovie.getTitle());

        SimpleDateFormat df = new SimpleDateFormat(getString(R.string.release_date_format), Locale.US);
        mReleaseDateTextView.setText(df.format(mMovie.getReleaseDate()));


        CharSequence userRating = Phrase.from(getString(R.string.userRating))
                .put(RATING_PHRASE, String.valueOf(mMovie.getUserRating())).format();
        mUserRatingTextView.setText(userRating);
        setActionBarTitle(mMovie.getTitle());

        mFloatingActionButton.setOnClickListener(new FavoriteMovieClickHandler(this, mMovie));

    }

    private void setupBar() {
        setSupportActionBar(mToolbar);

        mCollapsingToolbarLayout.setExpandedTitleColor(getHexColor(android.R.color.transparent));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intentThatStartedThisActivity = getIntent();
        mMovie = intentThatStartedThisActivity.getParcelableExtra(Constants.Intent.MOVIE);
        setupViewPager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private int getHexColor(int colorCode) {
        int color;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            color = getResources().getColor(colorCode, this.getTheme());
        } else {
            color = getResources().getColor(colorCode);
        }
        return color;
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        MovieSynopsisFragment synopsisFragment = new MovieSynopsisFragment();
        Bundle synopsisFragmentBundle = new Bundle();

        synopsisFragmentBundle.putParcelable(SELECTED_MOVIE, mMovie);
        synopsisFragment.setArguments(synopsisFragmentBundle);

        MovieReviewFragment movieReviewFragment = new MovieReviewFragment();
        Bundle movieReviewFragmentBundle = new Bundle();
        movieReviewFragmentBundle.putLong(MOVIE_ID, mMovie.getId());
        movieReviewFragment.setArguments(movieReviewFragmentBundle);

        MovieTrailerFragment movieTrailerFragment = new MovieTrailerFragment();
        Bundle movieTrailerFragmentBundle = new Bundle();
        movieTrailerFragmentBundle.putLong(MOVIE_ID, mMovie.getId());
        movieTrailerFragment.setArguments(movieTrailerFragmentBundle);

        adapter.addFragment(synopsisFragment, Constants.LABELS.SYNOPSIS);
        adapter.addFragment(movieReviewFragment, Constants.LABELS.REVIEW);
        adapter.addFragment(movieTrailerFragment, Constants.LABELS.TRAILER);

        viewPager.setAdapter(adapter);
    }

    private void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
