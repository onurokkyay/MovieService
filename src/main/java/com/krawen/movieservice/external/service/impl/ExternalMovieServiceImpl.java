package com.krawen.movieservice.external.service.impl;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.krawen.movieservice.entity.Genre;
import com.krawen.movieservice.entity.MovieDetail;
import com.krawen.movieservice.entity.MovieDetailDTO;
import com.krawen.movieservice.entity.RetrieveGenresResponse;
import com.krawen.movieservice.entity.SearchMovieResponseDTO;
import com.krawen.movieservice.external.mapper.ExternalMovieServiceMapper;
import com.krawen.movieservice.external.service.IExternalMovieService;
import com.krawen.movieservice.external.service.SearchMovieRequest;
import com.krawen.movieservice.external.service.SearchMovieResponse;
import com.krawen.movieservice.kafka.MovieKafkaProducer;
import com.krawen.movieservice.properties.MovieServiceProperties;

@Service
public class ExternalMovieServiceImpl implements IExternalMovieService {
	
	//@Autowired
	//RestTemplate restTemplate;
	
	@Autowired
	private MovieKafkaProducer kafkaProducer;
	
	@Autowired
	MovieServiceProperties movieServiceProperties;

	private HttpHeaders createHttpHeaders()
	{
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.add("Authorization","Bearer "+movieServiceProperties.getMovieApiBearerToken());
	    return headers;
	}

	@Override
	public MovieDetailDTO retrieveMovieById(int movieId) {
		RestTemplate restTemplate = new RestTemplate();
		ExternalMovieServiceMapper extMovieServiceMapper = new ExternalMovieServiceMapper();
		HttpHeaders headers = createHttpHeaders();
		
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
	    ResponseEntity<MovieDetail> response = restTemplate.exchange("https://api.themoviedb.org/3/movie/"+movieId, HttpMethod.GET, entity, MovieDetail.class);
	    MovieDetailDTO movie = extMovieServiceMapper.mapToMovieDetailDTO(response.getBody());
		return movie;
	}
	
	@Override
	public SearchMovieResponseDTO searchMovie(SearchMovieRequest request) {
		kafkaProducer.sendMessage(String.format("Movie searched by name: %s " ,request.getQuery()));
		RestTemplate restTemplate = new RestTemplate();
		ExternalMovieServiceMapper extMovieServiceMapper = new ExternalMovieServiceMapper();
		HttpHeaders headers = createHttpHeaders();

		URI uri = UriComponentsBuilder.fromHttpUrl("https://api.themoviedb.org/3/search/movie")
				.queryParam("query", request.getQuery())
				.queryParam("page", request.getPage())
				.queryParam("include_adult", request.isIncludeAdult())
				.build()
				.toUri();

		RequestEntity<?> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, uri);

		ResponseEntity<SearchMovieResponse> response = restTemplate.exchange(requestEntity,
				SearchMovieResponse.class);

		SearchMovieResponseDTO movies = extMovieServiceMapper
				.mapToSearchMovieByNameResponseDTO(response.getBody());
		return movies;
 
	}
	
	@Override
	public SearchMovieResponseDTO retrievePopularMovies(int page) {
		RestTemplate restTemplate = new RestTemplate();
		ExternalMovieServiceMapper extMovieServiceMapper = new ExternalMovieServiceMapper();
		HttpHeaders headers = createHttpHeaders();

		URI uri = UriComponentsBuilder.fromHttpUrl("https://api.themoviedb.org/3/movie/popular")
				.queryParam("page", page)
				.build()
				.toUri();

		RequestEntity<?> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, uri);

		ResponseEntity<SearchMovieResponse> response = restTemplate.exchange(requestEntity,
				SearchMovieResponse.class);

		SearchMovieResponseDTO movies = extMovieServiceMapper
				.mapToSearchMovieByNameResponseDTO(response.getBody());
		return movies;
 
	}
	
	@Override
	public List<Genre> retrieveGenres() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = createHttpHeaders();
		
		URI uri = UriComponentsBuilder.fromHttpUrl("https://api.themoviedb.org/3/genre/movie/list")
				.build()
				.toUri();

		RequestEntity<?> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, uri);

		ResponseEntity<RetrieveGenresResponse> response = restTemplate.exchange(requestEntity,
				RetrieveGenresResponse.class);

		return response.getBody().getGenres();
	}

}
