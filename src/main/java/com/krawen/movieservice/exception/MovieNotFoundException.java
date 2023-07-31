package com.krawen.movieservice.exception;

public class MovieNotFoundException extends Exception {

    public MovieNotFoundException(String movie) {
        super("Movie not found : " + movie);
    }

	public MovieNotFoundException(int movieId) {
		super("Movie not found with id : " + movieId);
	}

}
