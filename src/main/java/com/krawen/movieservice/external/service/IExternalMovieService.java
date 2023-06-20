package com.krawen.movieservice.external.service;

import com.krawen.movieservice.entity.MovieDetailDTO;
import com.krawen.movieservice.entity.SearchMovieByNameResponseDTO;

public interface IExternalMovieService {
	MovieDetailDTO retrieveMovie(String movieName);
	SearchMovieByNameResponseDTO retrieveMovieByName(String movieName);
}
