package com.krawen.movieservice.external.service;

import java.util.List;

import com.krawen.movieservice.entity.Genre;
import com.krawen.movieservice.entity.MovieDetailDTO;
import com.krawen.movieservice.entity.SearchMovieResponseDTO;

public interface IExternalMovieService {
	MovieDetailDTO retrieveMovieById(int movieId);
	SearchMovieResponseDTO searchMovie(SearchMovieRequest movieName);
	SearchMovieResponseDTO retrievePopularMovies(int page);
	List<Genre> retrieveGenres();
	SearchMovieResponseDTO discoverMovie(String withGenres);
}
