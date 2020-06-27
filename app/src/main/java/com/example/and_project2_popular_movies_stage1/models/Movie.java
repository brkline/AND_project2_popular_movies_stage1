package com.example.and_project2_popular_movies_stage1.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    public static final String PAGE_KEY = "page";
    public static final String TOTAL_RESULTS_KEY = "total_results";
    public static final String TOTAL_PAGES_KEY = "total_pages";
    public static final String VOTE_COUNT_KEY = "vote_count";
    public static final String ID_KEY = "id";
    public static final String VIDEO_KEY = "video";
    public static final String VOTE_AVERAGE_KEY = "vote_average";
    public static final String TITLE_KEY = "title";
    public static final String POPULARITY_KEY = "popularity";
    public static final String POSTER_PATH_KEY = "poster_path";
    public static final String ORIGINAL_LANGUAGE_KEY = "original_language";
    public static final String ORIGINAL_TITLE_KEY = "original_title";
    public static final String GENRE_IDS_KEY = "genre_ids";
    public static final String BACKDROP_PATH_KEY = "backdrop_path";
    public static final String ADULT_KEY = "adult";
    public static final String OVERVIEW_KEY = "overview";
    public static final String RELEASE_DATE_KEY = "release_date";
    public static final String RESULTS_KEY = "results";
    public static final String THEMOVIEDB_REQUEST_URL = "https://api.themoviedb.org/3/movie";
    public static final String API_KEY = "Get Your Own Key";
    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    private static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p";
    private static final String POSTER_SIZE = "w185";
    private String page;
    private String totalResults;
    private String totalPages;
    private String voteCount;
    private String id;
    private String video;
    private String voteAverage;
    private String title;
    private String popularity;
    private String posterPath;
    private String originalLanguage;
    private String originalTitle;
    private String genreIds;
    private String backdropPath;
    private String adult;
    private String overview;
    private String releaseDate;

    private Movie(Parcel in) {
        page = in.readString();
        totalResults = in.readString();
        totalPages = in.readString();
        voteCount = in.readString();
        id = in.readString();
        video = in.readString();
        voteAverage = in.readString();
        title = in.readString();
        popularity = in.readString();
        posterPath = in.readString();
        originalLanguage = in.readString();
        originalTitle = in.readString();
        genreIds = in.readString();
        backdropPath = in.readString();
        adult = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
    }

    public Movie() {
    }

    public Movie(String page, String totalResults, String totalPages, String voteCount, String id,
                 String video, String voteAverage, String title, String popularity, String posterPath,
                 String originalLanguage, String originalTitle, String genreIds, String
                         backdropPath, String adult, String overview, String releaseDate) {
        this.page = page;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
        this.voteCount = voteCount;
        this.id = id;
        this.video = video;
        this.voteAverage = voteAverage;
        this.title = title;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.genreIds = genreIds;
        this.backdropPath = backdropPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(page);
        dest.writeString(totalResults);
        dest.writeString(totalPages);
        dest.writeString(voteCount);
        dest.writeString(id);
        dest.writeString(video);
        dest.writeString(voteAverage);
        dest.writeString(title);
        dest.writeString(popularity);
        dest.writeString(posterPath);
        dest.writeString(originalLanguage);
        dest.writeString(originalTitle);
        dest.writeString(genreIds);
        dest.writeString(backdropPath);
        dest.writeString(adult);
        dest.writeString(overview);
        dest.writeString(releaseDate);
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(String genreIds) {
        this.genreIds = genreIds;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Uri getMoviePosterImage(String posterUrl) {
        // parse breaks apart the URI string that's passed into its parameter
        Uri moviePosterUri = Uri.parse(BASE_IMAGE_URL);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = moviePosterUri.buildUpon();

        // Append query parameter and its value.
        uriBuilder.appendPath(POSTER_SIZE);
        uriBuilder.appendEncodedPath(posterUrl);
        uriBuilder.appendQueryParameter("api_key", API_KEY);
        moviePosterUri = Uri.parse(uriBuilder.toString());
        return moviePosterUri;
    }
}
