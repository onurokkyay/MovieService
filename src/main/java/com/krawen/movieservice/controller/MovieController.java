package com.krawen.movieservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.krawen.movieservice.entity.MovieDetailDTO;
import com.krawen.movieservice.entity.SearchMovieResponseDTO;
import com.krawen.movieservice.external.service.SearchMovieRequest;
import com.krawen.movieservice.service.IMovieService;

@RestController
public class MovieController {
	
	@Autowired
	IMovieService movieService;

	@GetMapping("/movieservice/movie/{movieId}")
	public MovieDetailDTO retrieveMovieById(@PathVariable int movieId) {
		return movieService.retrieveMovieById(movieId);
	}
	
	@GetMapping("/movieservice/search/movie")
	public SearchMovieResponseDTO searchMovie(@RequestParam String movieName,
			@RequestParam(required = false) boolean includeAdult,
			@RequestParam(defaultValue = "1") int page) {
		SearchMovieRequest searchMovieRequest = new SearchMovieRequest(movieName,includeAdult,page);
		return movieService.searchMovie(searchMovieRequest);
	}
}
