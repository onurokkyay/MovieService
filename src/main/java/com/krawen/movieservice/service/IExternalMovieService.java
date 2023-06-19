package com.krawen.movieservice.service;

import com.krawen.movieservice.entity.Movie;

public interface IExternalMovieService {
	Movie retrieveMovie(String movieName);
}
