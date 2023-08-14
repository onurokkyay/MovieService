package com.krawen.movieservice.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
import com.krawen.movieservice.external.service.IExternalMovieService;
import com.krawen.movieservice.external.service.SearchMovieRequest;

@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {

	@InjectMocks
	MovieServiceImpl movieServiceImpl;
	
	@Mock
	IExternalMovieService extMovieService;
	
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
		when(extMovieService.searchMovie(searchMovieRequest)).thenReturn(searchMovieResponseDto);
		assertNotNull(movieServiceImpl.searchMovie(searchMovieRequest));
	}
	
	@Test
	void testRetrieveMovieByIdSuccess() throws MovieNotFoundException {
		when(extMovieService.retrieveMovieById(movieId)).thenReturn(movieDetailDto);
		assertEquals(movieId, movieServiceImpl.retrieveMovieById(movieId).getId());
	}
	
	@Test
	void testRetrieveMovieByIdMovieNotFoundException() throws MovieNotFoundException {
		when(extMovieService.retrieveMovieById(movieId)).thenThrow(MovieNotFoundException.class);
		assertThrows(MovieNotFoundException.class, () -> movieServiceImpl.retrieveMovieById(movieId));
	}
	
	@Test
	void testRetrievePopularMoviesSuccess() {
		when(extMovieService.retrievePopularMovies(page)).thenReturn(searchMovieResponseDto);
		assertNotNull(movieServiceImpl.retrievePopularMovies(page));
	}
	
	@Test
	void testDiscoverMovieSuccess() {
		when(extMovieService.discoverMovie(genres, page)).thenReturn(searchMovieResponseDto);
		assertNotNull(movieServiceImpl.discoverMovie(genres,page));
	}
	
	@Test
	void testRetrieveGenresSuccess() {
		when(extMovieService.retrieveGenres()).thenReturn(new ArrayList<>());
		assertNotNull(movieServiceImpl.retrieveGenres());
	}

}
