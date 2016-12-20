package com.example.rajat.myapplication;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;

public class MovieListTask extends AsyncTask<Void, Void, String[]> {

    private static final String TAG = "doInBackground";
    private static final String SEARCH = "Search";
    private static final String TITLE = "Title";
    private static final String IMDB_ID = "imdbID";
    private static final String POSTER = "Poster";
    private URI uri;
    private String[] id;
    private String[] url;
    OnQuerySearchListener onQuerySearchListener;

    public MovieListTask(String movieTitle, SearchFragment searchFragment) {
        Log.d("Name", movieTitle);
        try {
            uri = new URI(
                    "http",
                    "www.omdbapi.com",
                    "/",
                    "s=" + movieTitle,
                    null
            );
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        onQuerySearchListener = searchFragment;
    }

    @Override
    protected String[] doInBackground(Void... params) {
        String[] movie;
        String data = "";
        HttpURLConnection httpURLConnection = null;

        try {
            httpURLConnection = (HttpURLConnection) uri.toURL().openConnection();
            InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
            data = readStream(in);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

        Log.d(TAG, data);
        movie = parseJSON(data);
        return movie;
    }

    @Override
    protected void onPostExecute(String[] s) {
        if(s != null) {
            onQuerySearchListener.setMovieList(s, id, url);
            super.onPostExecute(s);
        }
    }

    private String readStream(InputStream in) {
        BufferedReader buf = null;
        StringBuffer data = new StringBuffer("");
        try {
            buf = new BufferedReader(new InputStreamReader(in));
            String line;

            while ((line = buf.readLine()) != null) {
                data.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (buf != null) {
                try {
                    buf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return data.toString();
    }

    private String[] parseJSON(String data) {
        String[] movie = null;
        String[] movieID = null;
        String[] posterUrl = null;

        try {
            JSONObject object = new JSONObject(data);
            JSONArray array = object.getJSONArray(SEARCH);

            movie = new String[array.length()];
            movieID = new String[array.length()];
            posterUrl = new String[array.length()];

            for (int i = 0; i < array.length(); i++) {
                movie[i] = array.getJSONObject(i).getString(TITLE);
                movieID[i] = array.getJSONObject(i).getString(IMDB_ID);
                posterUrl[i] = array.getJSONObject(i).getString(POSTER);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        id = movieID;
        url = posterUrl;

        return movie;
    }
}