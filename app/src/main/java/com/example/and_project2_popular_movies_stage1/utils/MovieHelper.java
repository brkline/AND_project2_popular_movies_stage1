package com.example.and_project2_popular_movies_stage1.utils;

import android.util.Log;

import com.example.and_project2_popular_movies_stage1.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MovieHelper {
    private static final String LOG_TAG = MovieHelper.class.getSimpleName();
    private static final int URL_READ_TIMEOUT = 10000;
    private static final int URL_SET_CONNECTION_TIMEOUT = 15000;

    /**
     * Query the MovieDB dataset and return a list of {@link Movie} objects.
     */
    public static List<Movie> fetchMovies(String requestUrl) {

        // Create URL object
        URL url = createUrl(requestUrl);
        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Return the list of {@link Movie}s
        return parseJson(jsonResponse);
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(URL_READ_TIMEOUT /* milliseconds */);
            urlConnection.setConnectTimeout(URL_SET_CONNECTION_TIMEOUT /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the MovieDB JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Movie} objects that has been built up from
     * parsing the given JSON response.
     */
    public static List<Movie> parseJson(String movieJSON) {

        // Create an empty ArrayList that we can start adding news items to
        List<Movie> movieList = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(movieJSON);

            // Get page and total results
            String currentPage = baseJsonResponse.getString(Movie.PAGE_KEY);
            String totalResults = baseJsonResponse.getString(Movie.TOTAL_RESULTS_KEY);
            String totalPages = baseJsonResponse.getString(Movie.TOTAL_PAGES_KEY);

            // Extract the JSONArray which represents a list of results from the query.
            JSONArray resultsArray = baseJsonResponse.getJSONArray(Movie.RESULTS_KEY);

            // For each movie in the resultsArray, create an {@link Movie} object
            for (int i = 0; i < resultsArray.length(); i++) {

                // Get a single movie at position i within the list of movies
                JSONObject currentMovie = resultsArray.getJSONObject(i);

                String voteCount = currentMovie.getString(Movie.VOTE_COUNT_KEY);
                String id = currentMovie.getString(Movie.ID_KEY);
                String video = currentMovie.getString(Movie.VIDEO_KEY);
                String voteAverage = currentMovie.getString(Movie.VOTE_AVERAGE_KEY);
                String title = currentMovie.getString(Movie.TITLE_KEY);
                String popularity = currentMovie.getString(Movie.POPULARITY_KEY);
                String posterPath = currentMovie.getString(Movie.POSTER_PATH_KEY);
                String originalLanguage = currentMovie.getString(Movie.ORIGINAL_LANGUAGE_KEY);
                String originalTitle = currentMovie.getString(Movie.ORIGINAL_TITLE_KEY);
                String genreIds = currentMovie.getString(Movie.GENRE_IDS_KEY);
                String backdropPath = currentMovie.getString(Movie.BACKDROP_PATH_KEY);
                String adult = currentMovie.getString(Movie.ADULT_KEY);
                String overview = currentMovie.getString(Movie.OVERVIEW_KEY);
                String releaseDate = currentMovie.getString(Movie.RELEASE_DATE_KEY);

                // Create a new {@link Movie} object
                Movie movie = new Movie(currentPage, totalResults, totalPages, voteCount, id,
                        video, voteAverage, title, popularity, posterPath, originalLanguage,
                        originalTitle, genreIds, backdropPath, adult, overview, releaseDate);

                // Add the new {@link Movie} to the list of movies.
                movieList.add(movie);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e(LOG_TAG, "Problem parsing the movie JSON results", e);
        }

        // Return the list of news items
        return movieList;
    }
}
