package com.krawen.movieservice.exception;

public class MovieAlreadyExistException extends Exception {

    public MovieAlreadyExistException(String movie) {
        super("Movie already exist in list : " + movie);
    }

	public MovieAlreadyExistException(int movieId) {
		super("Movie already exist in list with id : " + movieId);
	}

}
