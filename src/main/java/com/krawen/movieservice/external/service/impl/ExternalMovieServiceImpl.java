package com.krawen.movieservice.external.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.krawen.movieservice.entity.Movie;
import com.krawen.movieservice.entity.MovieDetail;
import com.krawen.movieservice.entity.MovieDetailDTO;
import com.krawen.movieservice.entity.SearchMovieByNameResponseDTO;
import com.krawen.movieservice.external.mapper.ExternalMovieServiceMapper;
import com.krawen.movieservice.external.service.IExternalMovieService;
import com.krawen.movieservice.external.service.SearchMovieByNameRequest;
import com.krawen.movieservice.properties.MovieServiceProperties;
import com.krawen.movieservice.external.service.SearchMovieByNameResponse;
import com.krawen.movieservice.external.service.SearchMovieByNameRequest;

@Service
public class ExternalMovieServiceImpl implements IExternalMovieService {
	
	//@Autowired
	//RestTemplate restTemplate;
	
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
	public MovieDetailDTO retrieveMovie(String movieName) {
		RestTemplate restTemplate = new RestTemplate();
		ExternalMovieServiceMapper extMovieServiceMapper = new ExternalMovieServiceMapper();
		HttpHeaders headers = createHttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
	    ResponseEntity<MovieDetail> response = restTemplate.exchange("https://api.themoviedb.org/3/movie/11", HttpMethod.GET, entity, MovieDetail.class);
	    MovieDetailDTO movie = extMovieServiceMapper.mapToMovieDetailDTO(response.getBody());
		return movie;
	}
	
	@Override
	public SearchMovieByNameResponseDTO retrieveMovieByName(String movieName) {
		RestTemplate restTemplate = new RestTemplate();
		ExternalMovieServiceMapper extMovieServiceMapper = new ExternalMovieServiceMapper();
		HttpHeaders headers = createHttpHeaders();
		SearchMovieByNameRequest request = new SearchMovieByNameRequest();
		request.setQuery(movieName);
		RequestEntity<SearchMovieByNameRequest> entity = new RequestEntity<SearchMovieByNameRequest>(request, headers,
				HttpMethod.GET, null);
		ResponseEntity<SearchMovieByNameResponse> response = restTemplate.exchange(
				"https://api.themoviedb.org/3/search/movie", HttpMethod.GET, entity, SearchMovieByNameResponse.class);
		SearchMovieByNameResponseDTO movie = extMovieServiceMapper
				.mapToSearchMovieByNameResponseDTO(response.getBody());
		return movie;
	}

}
