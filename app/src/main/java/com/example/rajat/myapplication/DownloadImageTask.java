package com.example.rajat.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadImageTask extends AsyncTask<Void, Void, Bitmap> {

    private String posterUrl;
    private ImageView movieListIcons;
    OnMovieSelectListener onMovieSelectListener = null;

    DownloadImageTask(String url, OnMovieSelectListener listener){

        posterUrl = url;
        onMovieSelectListener = listener;

    }

    DownloadImageTask(String url, ImageView movieIcons){

        posterUrl = url;
        movieListIcons = movieIcons;

    }

    @Override
    protected Bitmap doInBackground(Void... params) {

        return downloadImage(posterUrl);

    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        super.onPostExecute(bitmap);

        if(onMovieSelectListener != null) {
            onMovieSelectListener.setImage(bitmap);
        }
        else {
            if(movieListIcons.getTag() == posterUrl) {
                movieListIcons.setImageBitmap(bitmap);
            }
        }

    }

    public Bitmap downloadImage(final String moviePosterUrl){

        Bitmap poster = null;
        HttpURLConnection connection = null;

        try {
            URL url = new URL(moviePosterUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            poster = BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(connection != null) {
                connection.disconnect();
            }
        }

        return poster;

    }
}