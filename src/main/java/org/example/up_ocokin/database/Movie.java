package org.example.up_ocokin.database;

public class Movie {
    private int movieID;
    private String title;
    private int releaseYear;
    private int runtime;
    private String genres;  // жанры
    private String countries;  // страны
    private String reviews;  // комментарии

    // Конструктор
    public Movie(int movieID, String title, int releaseYear, int runtime, String genres, String countries, String reviews) {
        this.movieID = movieID;
        this.title = title;
        this.releaseYear = releaseYear;
        this.runtime = runtime;
        this.genres = genres;
        this.countries = countries;
        this.reviews = reviews;
    }

    // Геттеры и сеттеры
    public int getMovieID() {
        return movieID;
    }

    public String getTitle() {
        return title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getGenres() {
        return genres;
    }

    public String getCountries() {
        return countries;
    }

    public String getReviews() {
        return reviews;
    }

    // set-методы можно добавить по аналогии, если нужно
}
