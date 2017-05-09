package com.edwin.android.popularmovies.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Edwin Ramirez Ventur on 3/23/2017.
 */

public class Movie implements Parcelable {

    @SerializedName("id")
    private long id;
    @SerializedName("original_title")
    private String title;
    @SerializedName("poster_path")
    private String imagePosterUrl;
    @SerializedName("backdrop_path")
    private String imageBackdropUrl;
    @SerializedName("overview")
    private String synopsis;
    @SerializedName("vote_average")
    private double userRating;
    @SerializedName("release_date")
    private Date releaseDate;

    public Movie() {}

    protected Movie(Parcel in) {
        id = in.readLong();
        title = in.readString();
        imagePosterUrl = in.readString();
        imageBackdropUrl = in.readString();
        synopsis = in.readString();
        userRating = in.readDouble();
        releaseDate = new Date(in.readLong());
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };


    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(imagePosterUrl);
        dest.writeString(imageBackdropUrl);
        dest.writeString(synopsis);
        dest.writeDouble(userRating);
        dest.writeLong(releaseDate.getTime());
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagePosterUrl() {
        return imagePosterUrl;
    }

    public void setImagePosterUrl(String imagePosterUrl) {
        this.imagePosterUrl = imagePosterUrl;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public double getUserRating() {
        return userRating;
    }

    public void setUserRating(double userRating) {
        this.userRating = userRating;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getImageBackdropUrl() {
        return imageBackdropUrl;
    }

    public void setImageBackdropUrl(String imageBackdropUrl) {
        this.imageBackdropUrl = imageBackdropUrl;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", imagePosterUrl='" + imagePosterUrl + '\'' +
                ", imageBackdropUrl='" + imageBackdropUrl + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", userRating=" + userRating +
                ", releaseDate=" + releaseDate +
                '}';
    }
}
