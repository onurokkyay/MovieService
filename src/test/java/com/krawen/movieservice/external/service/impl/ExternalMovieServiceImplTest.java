package com.krawen.movieservice.external.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.krawen.movieservice.dto.MovieDetailDTO;
import com.krawen.movieservice.dto.SearchMovieResponseDTO;
import com.krawen.movieservice.dto.SearchPersonResponseDTO;
import com.krawen.movieservice.entity.MovieDetail;
import com.krawen.movieservice.exception.MovieNotFoundException;
import com.krawen.movieservice.external.service.SearchMovieRequest;
import com.krawen.movieservice.external.service.SearchMovieResponse;
import com.krawen.movieservice.external.service.dto.SearchPersonResponse;
import com.krawen.movieservice.kafka.MovieKafkaProducer;
import com.krawen.movieservice.properties.MovieServiceProperties;

@ExtendWith(MockitoExtension.class)
class ExternalMovieServiceImplTest {

	@InjectMocks
	ExternalMovieServiceImpl extMovieService;

	@Mock
	RestTemplate restTemplate;

	@Mock
	MovieServiceProperties movieServiceProperties;
	
	@Mock
	MovieKafkaProducer kafkaProducer;

	ResponseEntity<SearchPersonResponse> searchPersonResponseEntity;
	SearchPersonResponse searchPersonResponse;
	ResponseEntity<SearchMovieResponse> searchMovieResponseEntity;
	SearchMovieResponse searchMovieResponse;
	ResponseEntity<MovieDetail> retrieveMovieByIdResponseEntity;
	MovieDetail retrieveMovieByIdResponse;
	final String imagePath = "https://image.tmdb.org/t/p/w500/";

	String timeWindow = "day";
	int page = 1;
	String withGenres="Action";
	String movieName="TestMovieName";
	int movieId = 1;
	@BeforeEach
	void setup() {
		searchPersonResponse = new SearchPersonResponse();
		searchPersonResponse.setPage(1);
		searchPersonResponseEntity = new ResponseEntity<SearchPersonResponse>(searchPersonResponse, HttpStatus.OK);
		when(movieServiceProperties.getMovieApiBearerToken()).thenReturn("");
		searchMovieResponse = new SearchMovieResponse();
		searchMovieResponse.setPage(1L);
		searchMovieResponseEntity = new ResponseEntity<SearchMovieResponse>(searchMovieResponse, HttpStatus.OK);
		retrieveMovieByIdResponse = new  MovieDetail();
		retrieveMovieByIdResponse.setTitle(movieName);
		retrieveMovieByIdResponse.setBackdropPath("testPath");
		retrieveMovieByIdResponseEntity = new ResponseEntity<MovieDetail>(retrieveMovieByIdResponse, HttpStatus.OK);
	}
	
	@Test
    void testRetrieveMovieByIdSuccess() throws MovieNotFoundException {
    	
        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<MovieDetail>>any()))
             .thenReturn(retrieveMovieByIdResponseEntity);

        MovieDetailDTO retrieveMovieByIdResponseDto = extMovieService.retrieveMovieById(movieId);
        assertEquals(retrieveMovieByIdResponseDto.getTitle(), retrieveMovieByIdResponseEntity.getBody().getTitle());
    }
	
	@Test
    void testSearchMovieSuccess() {
    	
        when(restTemplate.exchange(
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<SearchMovieResponse>>any()))
             .thenReturn(searchMovieResponseEntity);

        SearchMovieResponseDTO searchMovieResponseDto = extMovieService.searchMovie(new SearchMovieRequest(movieName, false, page));
        assertEquals(searchMovieResponseDto.getPage(), searchMovieResponseEntity.getBody().getPage());
    }
	
	@Test
    void testRetrievePopularMoviesSuccess() {
    	
        when(restTemplate.exchange(
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<SearchMovieResponse>>any()))
             .thenReturn(searchMovieResponseEntity);

        SearchMovieResponseDTO retrievePopularMoviesResponseDto = extMovieService.retrievePopularMovies(page);
        assertEquals(retrievePopularMoviesResponseDto.getPage(), searchMovieResponseEntity.getBody().getPage());
    }
	
	@Test
    void testDiscoverMovieSuccess() {
    	
        when(restTemplate.exchange(
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<SearchMovieResponse>>any()))
             .thenReturn(searchMovieResponseEntity);

        SearchMovieResponseDTO discoverMovieResponseDto = extMovieService.discoverMovie(withGenres,page);
        assertEquals(discoverMovieResponseDto.getPage(), searchMovieResponseEntity.getBody().getPage());
    }

	@Test
    void testRetrieveTrendingPeopleSuccess() {
    	
        when(restTemplate.exchange(
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<SearchPersonResponse>>any()))
             .thenReturn(searchPersonResponseEntity);

        SearchPersonResponseDTO searchPersonDto = extMovieService.retrieveTrendingPeople(timeWindow);
        assertEquals(searchPersonDto.getPage(), searchPersonResponseEntity.getBody().getPage());
    }

}
