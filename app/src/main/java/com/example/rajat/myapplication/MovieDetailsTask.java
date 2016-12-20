package com.example.rajat.myapplication;

import android.os.AsyncTask;
import android.util.Log;
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

public class MovieDetailsTask extends AsyncTask<Void,Void,String> {

    private static final String TITLE = "Title";
    private static final String YEAR = "Year";
    private static final String RATED = "Rated";
    private static final String RELEASED = "Released";
    private static final String RUNTIME = "Runtime";
    private static final String GENRE = "Genre";
    private static final String DIRECTOR = "Director";
    private static final String WRITERS = "Writer";
    private static final String ACTORS = "Actors";
    private static final String PLOT = "Plot";
    private static final String AWARDS = "Awards";
    private static final String METASCORE = "Metascore";
    private static final String IMDB_RATING = "imdbRating";
    private static final String IMDB_VOTES = "imdbVotes";
    private static final String POSTER_URL = "Poster";
    private URI uri;
    OnMovieSelectListener onMovieSelectListener;


    public MovieDetailsTask(String id, MovieDetailsFragment movieDetailsFragment){

        try {
            uri = new URI("http",
                    "www.omdbapi.com",
                    "/",
                    "i=" + id + "&plot=full&r=json",
                    null);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        onMovieSelectListener = movieDetailsFragment;

    }

    @Override
    protected String doInBackground(Void... params) {

        HttpURLConnection httpURLConnection = null;
        String data = "";

        try {
            httpURLConnection = (HttpURLConnection) uri.toURL().openConnection();
        InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
        data = readStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            if(httpURLConnection != null){
            httpURLConnection.disconnect();
            }
        }

        Log.d("doInBackground", data);

        return data;

    }

    @Override
    protected void onPostExecute(String data) {

        parseJSON(data);
        super.onPostExecute(data);

    }

    private String readStream(InputStream in){

        BufferedReader buf = null;
        StringBuffer data = new StringBuffer("");
        try {
        buf = new BufferedReader(new InputStreamReader(in));
        String line;

            while((line = buf.readLine()) != null){
                data.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            if(buf != null){
                try {
                    buf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return data.toString();

    }

    private void parseJSON(String data){

        String title = null, year = null, rated = null, released = null, runtime = null,
               genre = null, director = null, writers = null, actors = null, plot = null,
               awards = null, metascore = null, imdbRating = null, imdbVotes = null, posterUrl = null;

        try {
            JSONObject object = new JSONObject(data);

            title = object.getString(TITLE);
            year = object.getString(YEAR);
            rated = object.getString(RATED);
            released = object.getString(RELEASED);
            runtime = object.getString(RUNTIME);
            genre = object.getString(GENRE);
            director = object.getString(DIRECTOR);
            writers = object.getString(WRITERS);
            actors = object.getString(ACTORS);
            plot = object.getString(PLOT);
            awards = object.getString(AWARDS);
            metascore = object.getString(METASCORE);
            imdbRating = object.getString(IMDB_RATING);
            imdbVotes = object.getString(IMDB_VOTES);
            posterUrl = object.getString(POSTER_URL);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        onMovieSelectListener.setMovieDetails(title, year, rated, released, runtime, genre, director, writers, actors,
                plot, awards, metascore, imdbRating, imdbVotes);

        DownloadImageTask downloadImageTask = new DownloadImageTask(posterUrl, onMovieSelectListener);
        downloadImageTask.execute();

    }
}