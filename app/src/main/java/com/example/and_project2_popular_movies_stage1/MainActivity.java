package com.example.and_project2_popular_movies_stage1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.and_project2_popular_movies_stage1.adapters.MovieAdapter;
import com.example.and_project2_popular_movies_stage1.models.Movie;
import com.example.and_project2_popular_movies_stage1.utils.CheckIfOnline;
import com.example.and_project2_popular_movies_stage1.utils.MovieLoader;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>>, SharedPreferences.OnSharedPreferenceChangeListener {

    // Constant value for the news items loader ID.
    private static final int MOVIE_LOADER_ID = 1;

    private List<Movie> movieList2;
    private GridView gridView;
    private TextView emptyStateTextView;
//    private static final String EXTRA_POSITION = "extra_position";
//    private static final String MOVIE_EXTRA = "movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emptyStateTextView = findViewById(R.id.empty_view);


        // CheckIfOnline class based on https://stackoverflow.com/a/27312494
        new CheckIfOnline(new CheckIfOnline.Consumer() {
            // Anonymous class created using https://stackoverflow.com/a/40826752
            @Override
            public void accept(Boolean internet) {
                /* do something with boolean response */
                if (internet) {
                    // Get a reference to the LoaderManager, in order to interact with loaders.
                    LoaderManager loaderManager = LoaderManager.getInstance(MainActivity.this);

                    // Initialize the loader. Pass in the int ID constant defined above and pass in null for
                    // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
                    // because this activity implements the LoaderCallbacks interface).
                    loaderManager.initLoader(MOVIE_LOADER_ID, null, MainActivity.this);

                } else {
                    // Set empty state text to display "No Internet Connection."
                    emptyStateTextView.setText(R.string.no_internet_connection);
                }
            }
        });

        // sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        setupSharedPreferences();
        // Get a reference to the GridView
        gridView = findViewById(R.id.main_fragment_gv);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                launchMovieDetailActivity(position);
            }
        });
    }

    @NonNull
    @Override
    public Loader<List<Movie>> onCreateLoader(int i, @Nullable Bundle bundle) {
        String url = Movie.THEMOVIEDB_REQUEST_URL;
        return new MovieLoader(this, url);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Movie>> loader, List<Movie> movieList) {

        // If there is a valid list of {@link Movie}s, then add them to the adapter's
        // data set.
        if (movieList != null && !movieList.isEmpty()) {

            MovieAdapter movieAdapter = new MovieAdapter(this, movieList);
            gridView.setAdapter(movieAdapter);
            movieList2 = movieList;

        } else {

            // Set empty state text to display "No Movies found."
            emptyStateTextView.setText(R.string.no_movies);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Movie>> loader) {
        loader.reset();
    }

    @Override
    // This method initialize the contents of the Activity's options menu.
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the Options Menu we specified in XML
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, MovieSettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void launchMovieDetailActivity(int position) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        // Add the extra data needed in the Movie Detail activity
        intent.putExtra(MovieDetailActivity.EXTRA_POSITION, position);
        Movie movie = movieList2.get(position);
        intent.putExtra(MovieDetailActivity.MOVIE_EXTRA, movie);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister VisualizerActivity as an OnPreferenceChangedListener to avoid any memory leaks.
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    private void setupSharedPreferences() {
        // Get all of the values from shared preferences to set it up
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sortOrderSelected = sharedPreferences.getString(getString(R.string
                .movie_settings_sort_order_key), getString(R.string.movie_settings_sort_label1));
        setTitle(sortOrderSelected);
        // Register the listener
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sortOrderSelected = sharedPreferences.getString(getString(R.string
                .movie_settings_sort_order_key), getString(R.string.movie_settings_sort_default));
        setTitle(sortOrderSelected);
    }
}