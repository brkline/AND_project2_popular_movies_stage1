package com.example.and_project2_popular_movies_stage1.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import androidx.loader.content.AsyncTaskLoader;

import com.example.and_project2_popular_movies_stage1.R;
import com.example.and_project2_popular_movies_stage1.models.Movie;

import java.util.List;

public class MovieLoader extends AsyncTaskLoader<List<Movie>> {
    /**
     * Query URL
     */
    private String url;

    /**
     * Constructs a new {@link MovieLoader}.
     *
     * @param context of the activity
     * @param url     to load data from
     */
    public MovieLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<Movie> loadInBackground() {
        if (url == null) {
            return null;
        }
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());

        String sortOrderSelected = sharedPrefs.getString(getContext().getString(R.string
                .movie_settings_sort_key), getContext().getString(R.string.movie_settings_sort_default));

        // parse breaks apart the URI string that's passed into its parameter
        Uri baseUri = Uri.parse(Movie.THEMOVIEDB_REQUEST_URL);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();

        // Append query parameter and its value.
        uriBuilder.appendPath(sortOrderSelected);
        uriBuilder.appendQueryParameter("api_key", Movie.API_KEY);
        url = uriBuilder.toString();
        // Perform the network request, parse the response, and extract a list of movies
        // items.
        List<Movie> movieList = MovieHelper.fetchMovies(url);
        return movieList;
    }
}
