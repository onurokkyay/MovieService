package com.krawen.movieservice.external.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.krawen.movieservice.dto.MovieDetailDTO;
import com.krawen.movieservice.dto.RetrievePersonDetailResponseDTO;
import com.krawen.movieservice.dto.SearchMovieResponseDTO;
import com.krawen.movieservice.dto.SearchPersonResponseDTO;
import com.krawen.movieservice.entity.BelongsToCollection;
import com.krawen.movieservice.entity.Genre;
import com.krawen.movieservice.entity.MovieDetail;
import com.krawen.movieservice.entity.RetrieveGenresResponse;
import com.krawen.movieservice.exception.MovieNotFoundException;
import com.krawen.movieservice.external.service.SearchMovieRequest;
import com.krawen.movieservice.external.service.SearchMovieResponse;
import com.krawen.movieservice.external.service.dto.RetrievePersonDetailResponse;
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
	static final  String IMAGE_PATH = "https://image.tmdb.org/t/p/w500/";
	ResponseEntity<RetrieveGenresResponse> retrieveGenresResponseEntity;
	RetrieveGenresResponse retrieveGenresResponse;
	String timeWindow = "day";
	int page = 1;
	String withGenres = "Action";
	String movieName = "TestMovieName";
	int movieId = 1;
	Long genreId = 1L;
	String genreName = "Action";
	Genre testGenre;
	String testPath = "testPath";
	BelongsToCollection belongsToCollection;
	ResponseEntity<RetrievePersonDetailResponse> retrievePersonDetailByIdResponseEntity;
	RetrievePersonDetailResponse retrievePersonDetailByIdResponse;
	String testName ="testName";
	int personId=1;

	@BeforeEach
	void setup() {
		searchPersonResponse = new SearchPersonResponse();
		searchPersonResponse.setPage(1);
		searchPersonResponseEntity = new ResponseEntity<SearchPersonResponse>(searchPersonResponse, HttpStatus.OK);
		when(movieServiceProperties.getMovieApiBearerToken()).thenReturn("");
		searchMovieResponse = new SearchMovieResponse();
		searchMovieResponse.setPage(1L);
		searchMovieResponseEntity = new ResponseEntity<SearchMovieResponse>(searchMovieResponse, HttpStatus.OK);
		retrieveMovieByIdResponse = new MovieDetail();
		retrieveMovieByIdResponse.setTitle(movieName);
		retrieveMovieByIdResponse.setBackdropPath(testPath);
		retrieveMovieByIdResponseEntity = new ResponseEntity<MovieDetail>(retrieveMovieByIdResponse, HttpStatus.OK);
		retrieveGenresResponse = new RetrieveGenresResponse();
		testGenre = new Genre();
		testGenre.setId(genreId);
		testGenre.setName(genreName);
		retrieveGenresResponse.setGenres(List.of(testGenre));
		retrieveGenresResponseEntity = new ResponseEntity<RetrieveGenresResponse>(retrieveGenresResponse,
				HttpStatus.OK);
		retrievePersonDetailByIdResponse = new RetrievePersonDetailResponse();
		retrievePersonDetailByIdResponse.setName(testName);
		retrievePersonDetailByIdResponseEntity = new ResponseEntity<RetrievePersonDetailResponse>(retrievePersonDetailByIdResponse, HttpStatus.OK);
	}

	@Test
    void testRetrieveMovieByIdSuccess() throws MovieNotFoundException {
    	
        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<MovieDetail>>any()))
             .thenReturn(retrieveMovieByIdResponseEntity);
        belongsToCollection= null;
        MovieDetailDTO retrieveMovieByIdResponseDto = extMovieService.retrieveMovieById(movieId);
        assertEquals(retrieveMovieByIdResponseDto.getTitle(), retrieveMovieByIdResponseEntity.getBody().getTitle());
    }
	
	@Test
    void testRetrieveMovieByIdSuccessBelongsToCollectionNotNull() throws MovieNotFoundException {
		belongsToCollection = new BelongsToCollection();
		belongsToCollection.setBackdropPath(testPath);
		retrieveMovieByIdResponse.setBelongsToCollection(belongsToCollection);
		
        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<MovieDetail>>any()))
             .thenReturn(retrieveMovieByIdResponseEntity);

        MovieDetailDTO retrieveMovieByIdResponseDto = extMovieService.retrieveMovieById(movieId);
        assertEquals(retrieveMovieByIdResponseDto.getTitle(), retrieveMovieByIdResponseEntity.getBody().getTitle());
        assertTrue(retrieveMovieByIdResponseDto.getBelongsToCollection().getBackdropPath().startsWith(IMAGE_PATH));
    }

	@Test
    void testRetrieveMovieByIdMovieNotFoundException() throws MovieNotFoundException {
    	
        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<MovieDetail>>any()))
             .thenThrow(new HttpClientErrorException (HttpStatus.NOT_FOUND));

        assertThrows(MovieNotFoundException.class, () -> extMovieService.retrieveMovieById(movieId));
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
	void testRetrieveGenresSuccess() {
		when(restTemplate.exchange(
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<RetrieveGenresResponse>>any()))
             .thenReturn(retrieveGenresResponseEntity);
		
		List<Genre> genreListResponse = extMovieService.retrieveGenres();
		System.out.println(genreListResponse);
		Genre testGenre = genreListResponse.stream().filter(genre -> genreId.equals(genre.getId())).findFirst().orElse(null);
		System.out.println(testGenre);
		assertNotNull(testGenre);
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
	
	@Test
    void testRetrievePersonDetailByIdSuccess() {
    	
        when(restTemplate.exchange(
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<RetrievePersonDetailResponse>>any()))
             .thenReturn(retrievePersonDetailByIdResponseEntity);

        RetrievePersonDetailResponseDTO retrievePersonDetailByIdResponse = extMovieService.retrievePersonDetailById(personId);
        assertEquals(retrievePersonDetailByIdResponse.getName(), retrievePersonDetailByIdResponseEntity.getBody().getName());
    }

}
