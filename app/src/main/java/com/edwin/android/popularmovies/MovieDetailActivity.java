package com.edwin.android.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.edwin.android.popularmovies.entity.Movie;
import com.edwin.android.popularmovies.util.Constants;
import com.squareup.phrase.Phrase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String TAG = MovieDetailActivity.class.getSimpleName();

    private ImageView mMovieThumbnailImageView;
    private TextView mMovieNameTextView;
    private TextView mReleaseDateTextView;
    private TextView mUserRatingTextView;
    private TextView mSynopsisTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mMovieThumbnailImageView = (ImageView) findViewById(R.id.iv_movie_thumbnail);
        mMovieNameTextView = (TextView) findViewById(R.id.tv_movie_name);
        mReleaseDateTextView = (TextView) findViewById(R.id.tv_release_date);
        mUserRatingTextView = (TextView) findViewById(R.id.tv_user_rating);
        mSynopsisTextView = (TextView) findViewById(R.id.tv_synopsis);

        Intent intentThatStartedThisActivity = getIntent();

        Movie movie = intentThatStartedThisActivity.getParcelableExtra(Constants.Intent.MOVIE);

        Log.d(TAG, "Movie received: " + movie);

        if (movie == null) {
            Log.d(TAG, "The movie value is null, end the activity");
            return;
        }

        Picasso picasso = Picasso.with(this);
        picasso.load(movie.getImagePosterUrl()).fit().into(mMovieThumbnailImageView);
        mMovieNameTextView.setText(movie.getTitle());

        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        CharSequence releaseDateText = Phrase.from(getString(R.string.releaseDate))
                .put("date", df.format(movie.getReleaseDate()))
                .format();
        mReleaseDateTextView.setText(releaseDateText);


        CharSequence userRating = Phrase.from(getString(R.string.userRating))
                .put("rating", String.valueOf(movie.getUserRating())).format();
        mUserRatingTextView.setText(userRating );
        mSynopsisTextView.setText(movie.getSynopsis());


    }
}
