package com.example.rajat.myapplication;

import android.graphics.Bitmap;

public interface OnMovieSelectListener {
    void setMovieDetails(String title, String year, String rated, String released, String runtime,
                         String genre, String director, String writers, String actors, String plot,
                         String awards, String metascore, String imdbRating, String imdbVotes);

    void setImage(Bitmap poster);
}