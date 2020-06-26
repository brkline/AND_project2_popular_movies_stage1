package com.example.and_project2_popular_movies_stage1.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.and_project2_popular_movies_stage1.R;
import com.example.and_project2_popular_movies_stage1.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the List is the data we want
     * to populate into the lists
     *
     * @param context   The current context. Used to inflate the layout file.
     * @param movieList A List of AndroidFlavor objects to display in a list
     */
    public MovieAdapter(Activity context, List<Movie> movieList) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, movieList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Gets the AndroidFlavor object from the ArrayAdapter at the appropriate position
        Movie movieList = getItem(position);

        // If this is a new View object we're getting, then inflate the layout.
        // If not, this view already has the layout inflated from a previous call to getView,
        // and we modify the View widgets as usual.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_item, parent, false);
        }

        ImageView moviePosterView = (ImageView) convertView.findViewById(R.id.poster_image_iv);


        if (movieList != null) {
            String posterImageUrl = movieList.getPosterPath();

            Picasso.with(convertView.getContext())
                    .load(movieList.getMoviePosterImage(posterImageUrl))
                    .into(moviePosterView);
            moviePosterView.setContentDescription(movieList.getTitle());

        }

        return convertView;
    }

}
