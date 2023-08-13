package com.krawen.movieservice.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.krawen.movieservice.dto.SearchMovieResponseDTO;
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

	@BeforeEach
	void setup() {
		searchMovieResponseDto = new SearchMovieResponseDTO();
		searchMovieRequest = new SearchMovieRequest(movieName, isAdult, page);
	}

	@Test
	void testRetrieveMovieByIdSuccess() {
		when(movieService.searchMovie(searchMovieRequest)).thenReturn(searchMovieResponseDto);
		assertNotNull(movieController.searchMovie(movieName, isAdult, page));
		
	}

}
