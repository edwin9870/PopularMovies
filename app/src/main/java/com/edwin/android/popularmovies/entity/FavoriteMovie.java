package com.edwin.android.popularmovies.entity;

import java.io.Serializable;

/**
 * Created by Edwin Ramirez Ventur on 5/8/2017.
 */

public class FavoriteMovie implements Serializable {

    private static final long serialVersionUID = -7973522046531247768L;

    private long id;
    private long movieId;
    private String title;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FavoriteMovie that = (FavoriteMovie) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "FavoriteMovie{" +
                "id=" + id +
                ", movieId=" + movieId +
                ", title='" + title + '\'' +
                '}';
    }
}
