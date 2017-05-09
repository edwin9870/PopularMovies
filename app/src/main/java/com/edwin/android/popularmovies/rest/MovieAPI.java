package com.edwin.android.popularmovies.rest;

import com.edwin.android.popularmovies.entity.MovieResponse;
import com.edwin.android.popularmovies.entity.MovieReviewResponse;
import com.edwin.android.popularmovies.entity.MovieTrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Edwin Ramirez Ventur on 5/8/2017.
 */

public interface MovieAPI {

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRated(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<MovieResponse> getTopPopular(@Query("api_key") String apiKey);

    @GET("movie/{movieId}/reviews")
    Call<MovieReviewResponse> getMovieReviews(@Path("movieId") long movieId, @Query("api_key") String apiKey);

    @GET("movie/{movieId}/videos")
    Call<MovieTrailerResponse> getMovieTrailers(@Path("movieId") long movieId, @Query("api_key") String apiKey);


}
