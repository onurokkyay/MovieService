package com.krawen.movieservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.krawen.movieservice.dto.MovieDetailDTO;
import com.krawen.movieservice.dto.SearchMovieResponseDTO;
import com.krawen.movieservice.exception.MovieNotFoundException;
import com.krawen.movieservice.external.service.SearchMovieRequest;
import com.krawen.movieservice.service.IMovieService;

@ExtendWith(MockitoExtension.class)
class MovieControllerTest {

	@InjectMocks
	MovieController movieController;

	@Mock
	IMovieService movieService;

	SearchMovieResponseDTO searchMovieResponseDto;
	SearchMovieRequest searchMovieRequest;
	String movieName = "testMovieName";
	int page = 1;
	boolean isAdult = false;
	int movieId = 1;
	MovieDetailDTO movieDetailDto;
	String genres = "testGenre";

	@BeforeEach
	void setup() {
		searchMovieResponseDto = new SearchMovieResponseDTO();
		searchMovieRequest = new SearchMovieRequest(movieName, isAdult, page);
		movieDetailDto = new MovieDetailDTO();
		movieDetailDto.setId(movieId);
	}

	@Test
	void testSearchMovieByIdSuccess() {
		when(movieService.searchMovie(searchMovieRequest)).thenReturn(searchMovieResponseDto);
		assertNotNull(movieController.searchMovie(movieName, isAdult, page));
	}
	
	@Test
	void testRetrieveMovieByIdSuccess() throws MovieNotFoundException {
		when(movieService.retrieveMovieById(movieId)).thenReturn(movieDetailDto);
		assertEquals(movieId, movieController.retrieveMovieById(movieId).getId());
	}
	
	@Test
	void testRetrieveMovieByIdMovieNotFoundException() throws MovieNotFoundException {
		when(movieService.retrieveMovieById(movieId)).thenThrow(MovieNotFoundException.class);
		assertThrows(MovieNotFoundException.class, () -> movieController.retrieveMovieById(movieId));
	}
	
	@Test
	void testRetrievePopularMoviesSuccess() {
		when(movieService.retrievePopularMovies(page)).thenReturn(searchMovieResponseDto);
		assertNotNull(movieController.retrievePopularMovies(page));
	}
	
	@Test
	void testDiscoverMovieSuccess() {
		when(movieService.discoverMovie(genres, page)).thenReturn(searchMovieResponseDto);
		assertNotNull(movieController.discoverMovie(page,genres));
	}
	
	@Test
	void testRetrieveGenresSuccess() {
		when(movieService.retrieveGenres()).thenReturn(new ArrayList<>());
		assertNotNull(movieController.retrieveGenres());
	}

}
