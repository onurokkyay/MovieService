package com.krawen.movieservice.service;

import java.util.List;

import com.krawen.movieservice.dto.MovieDetailDTO;
import com.krawen.movieservice.dto.SearchMovieResponseDTO;
import com.krawen.movieservice.entity.Genre;
import com.krawen.movieservice.exception.MovieNotFoundException;
import com.krawen.movieservice.external.service.SearchMovieRequest;

public interface IMovieService {
	MovieDetailDTO retrieveMovieById(int movieId) throws MovieNotFoundException;
	SearchMovieResponseDTO searchMovie(SearchMovieRequest request);
	SearchMovieResponseDTO retrievePopularMovies(int page);
	List<Genre> retrieveGenres();
	SearchMovieResponseDTO discoverMovie(String withGenres, int page);
}
