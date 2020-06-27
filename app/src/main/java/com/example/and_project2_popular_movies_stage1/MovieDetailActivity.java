package com.example.and_project2_popular_movies_stage1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.and_project2_popular_movies_stage1.models.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    public static final String MOVIE_EXTRA = "movie";
    private static final int DEFAULT_POSITION = -1;
    private static final String RELEASE_DATE_SEPARATOR = "-";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        // Code below based on Udacity Sandwich App starter code
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
        }

        // We should have been passed a Movie object so retrieve it
        Movie movie = intent.getParcelableExtra(MOVIE_EXTRA);
        if (movie == null) {
            closeOnError();
        }

        populateUI(movie);

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Movie movieIn) {

        TextView titleTextView = findViewById(R.id.title_tv);
        ImageView posterImageView = findViewById(R.id.poster_image_iv);
        TextView releaseDateTextView = findViewById(R.id.release_date_tv);
        TextView averageRatingTextView = findViewById(R.id.average_rating_tv);
        TextView synopsisTextView = findViewById(R.id.synopsis_tv);

        titleTextView.setText(movieIn.getTitle());

        String posterImageUrl = movieIn.getPosterPath();
        Picasso.get()
                .load(movieIn.getMoviePosterImage(posterImageUrl))
                .into(posterImageView);
        posterImageView.setContentDescription(movieIn.getTitle());

        // Extract the Release Year out of the Release Date so we can populate the
        // TextView
        String releaseDate = movieIn.getReleaseDate();
        if (!(releaseDate.isEmpty())) {
            String[] releaseDateStr = releaseDate.split(RELEASE_DATE_SEPARATOR);
            String releaseYear = releaseDateStr[0];
            releaseDateTextView.setText(releaseYear);
            // We didn't get a Release Date for some reason so populate the TextView with
            // "Unknown"
        } else {
            releaseDateTextView.setText(R.string.release_date_unknown);
        }

        averageRatingTextView.setText(getString(R.string.average_rating, movieIn.getVoteAverage()));
        synopsisTextView.setText(movieIn.getOverview());

    }
}
