package com.krawen.movieservice.exception;

public class MovieNotFoundException extends Exception {

    public MovieNotFoundException(String movie) {
        super("Movie not found : " + movie);
    }

}
