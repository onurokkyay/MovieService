package com.krawen.movieservice.service;

import java.util.List;

import com.krawen.movieservice.entity.Genre;
import com.krawen.movieservice.entity.MovieDetailDTO;
import com.krawen.movieservice.entity.SearchMovieResponseDTO;
import com.krawen.movieservice.external.service.SearchMovieRequest;

public interface IMovieService {
	MovieDetailDTO retrieveMovieById(int movieId);
	SearchMovieResponseDTO searchMovie(SearchMovieRequest request);
	SearchMovieResponseDTO retrievePopularMovies(int page);
	List<Genre> retrieveGenres();
	SearchMovieResponseDTO discoverMovie(String withGenres, int page);
}
