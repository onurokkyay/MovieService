package com.krawen.movieservice.external.service;

import java.util.List;

import com.krawen.movieservice.dto.MovieDetailDTO;
import com.krawen.movieservice.dto.RetrievePersonDetailResponseDTO;
import com.krawen.movieservice.dto.SearchMovieResponseDTO;
import com.krawen.movieservice.dto.SearchPersonResponseDTO;
import com.krawen.movieservice.entity.Genre;
import com.krawen.movieservice.exception.MovieNotFoundException;

public interface IExternalMovieService {
	MovieDetailDTO retrieveMovieById(int movieId) throws MovieNotFoundException;
	SearchMovieResponseDTO searchMovie(SearchMovieRequest movieName);
	SearchMovieResponseDTO retrievePopularMovies(int page);
	List<Genre> retrieveGenres();
	SearchMovieResponseDTO discoverMovie(String withGenres, int page);
	SearchPersonResponseDTO retrieveTrendingPeople(String timeWindow);
	RetrievePersonDetailResponseDTO retrievePersonDetailById(int personId);
}
