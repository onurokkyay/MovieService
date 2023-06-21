package com.krawen.movieservice.external.service;

import com.krawen.movieservice.entity.MovieDetailDTO;
import com.krawen.movieservice.entity.SearchMovieResponseDTO;

public interface IExternalMovieService {
	MovieDetailDTO retrieveMovie(String movieName);
	SearchMovieResponseDTO searchMovie(SearchMovieRequest movieName);
}
