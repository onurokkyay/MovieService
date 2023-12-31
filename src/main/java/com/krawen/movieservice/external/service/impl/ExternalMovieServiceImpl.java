package com.krawen.movieservice.external.service.impl;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.krawen.movieservice.dto.MovieDetailDTO;
import com.krawen.movieservice.dto.RetrievePersonDetailResponseDTO;
import com.krawen.movieservice.dto.SearchMovieResponseDTO;
import com.krawen.movieservice.dto.SearchPersonResponseDTO;
import com.krawen.movieservice.entity.Genre;
import com.krawen.movieservice.entity.MovieDetail;
import com.krawen.movieservice.entity.RetrieveGenresResponse;
import com.krawen.movieservice.exception.MovieNotFoundException;
import com.krawen.movieservice.external.mapper.ExternalMovieServiceMapper;
import com.krawen.movieservice.external.service.IExternalMovieService;
import com.krawen.movieservice.external.service.SearchMovieRequest;
import com.krawen.movieservice.external.service.SearchMovieResponse;
import com.krawen.movieservice.external.service.dto.RetrievePersonDetailResponse;
import com.krawen.movieservice.external.service.dto.SearchPersonResponse;
import com.krawen.movieservice.kafka.MovieKafkaProducer;
import com.krawen.movieservice.properties.MovieServiceProperties;

@Service
public class ExternalMovieServiceImpl implements IExternalMovieService {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private MovieKafkaProducer kafkaProducer;

	@Autowired
	MovieServiceProperties movieServiceProperties;

	private static final String BASE_URL = "https://api.themoviedb.org/3";

	HttpHeaders createHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Bearer " + movieServiceProperties.getMovieApiBearerToken());
		return headers;
	}

	@Override
	public MovieDetailDTO retrieveMovieById(int movieId) throws MovieNotFoundException {
		ExternalMovieServiceMapper extMovieServiceMapper = new ExternalMovieServiceMapper();
		HttpHeaders headers = createHttpHeaders();

		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		ResponseEntity<MovieDetail> response = null;
		try {
			response = restTemplate.exchange(BASE_URL + "/movie/" + movieId, HttpMethod.GET, entity, MovieDetail.class);
		} catch (HttpClientErrorException e) {
			if (HttpStatus.NOT_FOUND.equals(e.getStatusCode())) {
				throw new MovieNotFoundException(movieId);
			}
		}

		MovieDetailDTO movie = extMovieServiceMapper.mapToMovieDetailDTO(response.getBody());
		return movie;
	}

	@Override
	public SearchMovieResponseDTO searchMovie(SearchMovieRequest request) {
		kafkaProducer.sendMessage(String.format("Movie searched by name: %s ", request.getQuery()));
		ExternalMovieServiceMapper extMovieServiceMapper = new ExternalMovieServiceMapper();
		HttpHeaders headers = createHttpHeaders();
		URI uri = UriComponentsBuilder.fromHttpUrl(BASE_URL).path("/search/movie")
				.queryParam("query", request.getQuery()).queryParam("page", request.getPage())
				.queryParam("include_adult", request.isIncludeAdult()).build().toUri();

		RequestEntity<?> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, uri);

		ResponseEntity<SearchMovieResponse> response = restTemplate.exchange(requestEntity, SearchMovieResponse.class);

		SearchMovieResponseDTO movies = extMovieServiceMapper.mapToSearchMovieByNameResponseDTO(response.getBody());
		return movies;

	}

	@Override
	public SearchMovieResponseDTO retrievePopularMovies(int page) {
		ExternalMovieServiceMapper extMovieServiceMapper = new ExternalMovieServiceMapper();
		HttpHeaders headers = createHttpHeaders();

		URI uri = UriComponentsBuilder.fromHttpUrl(BASE_URL).path("/movie/popular").queryParam("page", page).build()
				.toUri();

		RequestEntity<?> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, uri);

		ResponseEntity<SearchMovieResponse> response = restTemplate.exchange(requestEntity, SearchMovieResponse.class);

		SearchMovieResponseDTO movies = extMovieServiceMapper.mapToSearchMovieByNameResponseDTO(response.getBody());
		return movies;

	}

	@Override
	public List<Genre> retrieveGenres() {
		HttpHeaders headers = createHttpHeaders();

		URI uri = UriComponentsBuilder.fromHttpUrl(BASE_URL).path("/genre/movie/list").build().toUri();

		RequestEntity<?> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, uri);

		ResponseEntity<RetrieveGenresResponse> response = restTemplate.exchange(requestEntity,
				RetrieveGenresResponse.class);

		return response.getBody().getGenres();
	}

	@Override
	public SearchMovieResponseDTO discoverMovie(String withGenres, int page) {
		ExternalMovieServiceMapper extMovieServiceMapper = new ExternalMovieServiceMapper();
		HttpHeaders headers = createHttpHeaders();

		URI uri = UriComponentsBuilder.fromHttpUrl(BASE_URL).path("/discover/movie")
				.queryParam("with_genres", withGenres).queryParam("page", page).build().toUri();

		RequestEntity<?> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, uri);

		ResponseEntity<SearchMovieResponse> response = restTemplate.exchange(requestEntity, SearchMovieResponse.class);

		SearchMovieResponseDTO movies = extMovieServiceMapper.mapToSearchMovieByNameResponseDTO(response.getBody());
		return movies;

	}
	
	@Override
	public SearchPersonResponseDTO retrieveTrendingPeople(String timeWindow) {
		ExternalMovieServiceMapper extMovieServiceMapper = new ExternalMovieServiceMapper();
		HttpHeaders headers = createHttpHeaders();

		URI uri = UriComponentsBuilder.fromHttpUrl(BASE_URL).path("/trending/person/"+timeWindow).build()
				.toUri();
	
		RequestEntity<?> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, uri);

		ResponseEntity<SearchPersonResponse> response = restTemplate.exchange(requestEntity, SearchPersonResponse.class);

		SearchPersonResponseDTO searchPersonResponseDto = extMovieServiceMapper.mapToSearchPeopleResponseDTO(response.getBody());
		return searchPersonResponseDto;

	}
	
	@Override
	public RetrievePersonDetailResponseDTO retrievePersonDetailById(int personId) {
		ExternalMovieServiceMapper extMovieServiceMapper = new ExternalMovieServiceMapper();
		HttpHeaders headers = createHttpHeaders();

		URI uri = UriComponentsBuilder.fromHttpUrl(BASE_URL).path("/person/"+personId).build()
				.toUri();
	
		RequestEntity<?> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, uri);

		ResponseEntity<RetrievePersonDetailResponse> response = restTemplate.exchange(requestEntity, RetrievePersonDetailResponse.class);

		RetrievePersonDetailResponseDTO searchPersonResponseDto = extMovieServiceMapper.mapToRetrievePersonDetailResponseDTO(response.getBody());
		return searchPersonResponseDto;

	}

}
