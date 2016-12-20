package com.example.rajat.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SearchFragment extends Fragment implements OnQuerySearchListener {

    private EditText movieName;
    private ListView movieList;
    private String[] movies;
    private String[] imdbID;
    OnItemSelectListener onItemSelectListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_search, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        Button search, reset;

        movieName = (EditText) getActivity().findViewById(R.id.search_edit_text);
        movieList = (ListView) getActivity().findViewById(R.id.movies_list_view);
        search = (Button) getActivity().findViewById(R.id.search_button);
        reset = (Button) getActivity().findViewById(R.id.reset_button);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                movieName.setText("");

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String movieTitle = movieName.getText().toString();
                MovieListTask movieListTask = new MovieListTask(movieTitle, SearchFragment.this);
                movieListTask.execute();

            }
        });


        movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d("Movie Selected", movies[position]);
                Log.d("IMDb ID", imdbID[position]);

                MovieDetailsFragment movieDetailsFragment = MovieDetailsFragment.newInstance(imdbID[position]);

                onItemSelectListener.itemSelected(movieDetailsFragment);

            }
        });
    }

    @Override
    public void setMovieList(String[] string, String[] id, String[] url) {

        movies = string;
        imdbID = id;

        CustomListAdapter adapter = new CustomListAdapter(getActivity(), R.layout.custom_list_layout, movies, url);
        movieList.setAdapter(adapter);

    }

    public void setActivityInstance(MainActivity mainActivity){

        onItemSelectListener = mainActivity;

    }

    public class CustomListAdapter extends ArrayAdapter<String> {

        private final Activity context;
        private String[] posterUrl;

        public CustomListAdapter(Activity context, int resource, String[] movies, String[] url) {

            super(context, resource, movies);
            this.context = context;
            posterUrl = url;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null) {
                LayoutInflater inflater = context.getLayoutInflater();
                convertView = inflater.inflate(R.layout.custom_list_layout, parent, false);
            }

            TextView movieTitles = (TextView) convertView.findViewById(R.id.movie_titles_text_view);
            ImageView movieIcons = (ImageView) convertView.findViewById(R.id.movie_icons_image_view);

            movieIcons.setImageDrawable(null);
            movieIcons.setTag(posterUrl[position]);
            DownloadImageTask downloadImageTask = new DownloadImageTask(posterUrl[position], movieIcons);
            downloadImageTask.execute();

            movieTitles.setText(getItem(position));

            return convertView;

        }
    }
}