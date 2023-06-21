package com.krawen.movieservice.service;

import com.krawen.movieservice.entity.MovieDetailDTO;
import com.krawen.movieservice.entity.SearchMovieResponseDTO;
import com.krawen.movieservice.external.service.SearchMovieRequest;

public interface IMovieService {
	MovieDetailDTO retrieveMovie(String movieName);
	SearchMovieResponseDTO searchMovie(SearchMovieRequest request);
}
