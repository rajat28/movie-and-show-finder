package com.example.rajat.myapplication;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieDetailsFragment extends Fragment implements OnMovieSelectListener {

    private static final String IMDB_ID = "imdb_id";
    private TextView titleYear, rated, released, runtime, genre, director, writers,
            actors, plot, awards, metascore, imdbRating, imdbVotes;
    private ImageView moviePoster;

    public static MovieDetailsFragment newInstance(final String imdbId) {

        final Bundle arguments = new Bundle();
        arguments.putString(IMDB_ID, imdbId);

        MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
        movieDetailsFragment.setArguments(arguments);

        return movieDetailsFragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_movie_details, container, false);

        titleYear = (TextView) view.findViewById(R.id.title_year_text_view);
        rated = (TextView) view.findViewById(R.id.rated_text_view);
        genre = (TextView) view.findViewById(R.id.genre_text_view);
        runtime = (TextView) view.findViewById(R.id.runtime_text_view);
        plot = (TextView) view.findViewById(R.id.plot_text_view);
        imdbRating = (TextView) view.findViewById(R.id.imdb_rating_text_view);
        imdbVotes = (TextView) view.findViewById(R.id.imdb_votes_text_view);
        metascore = (TextView) view.findViewById(R.id.metascore_text_view);
        awards = (TextView) view.findViewById(R.id.awards_text_view);
        released = (TextView) view.findViewById(R.id.released_text_view);
        actors = (TextView) view.findViewById(R.id.actors_text_view);
        director = (TextView) view.findViewById(R.id.director_text_view);
        writers = (TextView) view.findViewById(R.id.writers_text_view);
        moviePoster = (ImageView) view.findViewById(R.id.poster_image_view);

        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        String imdbID = getArguments().getString(IMDB_ID);
        MovieDetailsTask movieDetailsTask = new MovieDetailsTask(imdbID, this);
        movieDetailsTask.execute();

    }

    @Override
    public void setMovieDetails(String title, String year, String rated, String released, String runtime,
                                String genre, String director, String writers, String actors, String plot,
                                String awards, String metascore, String imdbRating, String imdbVotes) {

        titleYear.setText(title + "  (" + year + ")  ");
        this.rated.setText(rated);
        this.genre.setText(genre);
        this.runtime.setText(runtime);
        this.plot.setText(plot);
        this.imdbRating.setText("IMDb Rating: " + imdbRating + "/10");
        this.imdbVotes.setText("(" + imdbVotes + ")");
        this.metascore.setText("Metascore: " + metascore);
        this.awards.setText("Awards: " + awards);
        this.released.setText("Release Date\n" + released);
        this.actors.setText("Top Billed Cast\n" + actors);
        this.director.setText("Director\n" + director);
        this.writers.setText("Writers\n" + writers);

    }

    @Override
    public void setImage(Bitmap poster) {

        moviePoster.setImageBitmap(poster);

    }
}