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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
public class MovieController {
	
	@Autowired
	IMovieService movieService;

	@GetMapping("/movieservice/movies/{movieId}")
	@Operation(summary = "Retrieve a movie by its ID", description = "Returns the movie details for the given movie ID")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Successful retrieval of movie details"),
	    @ApiResponse(responseCode = "404", description = "Movie not found")
	})
	public MovieDetailDTO retrieveMovieById(@PathVariable int movieId) {
		return movieService.retrieveMovieById(movieId);
	}
	
	@GetMapping("/movieservice/movies")
	@Operation(summary = "Search movies", description = "Searches movies based on the provided criteria")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Successful retrieval of movie search results"),
	    @ApiResponse(responseCode = "400", description = "Invalid request parameters")
	})
	public SearchMovieResponseDTO searchMovie(@RequestParam String movieName,
			@RequestParam(required = false) boolean includeAdult,
			@RequestParam(defaultValue = "1") int page) {
		SearchMovieRequest searchMovieRequest = new SearchMovieRequest(movieName,includeAdult,page);
		return movieService.searchMovie(searchMovieRequest);
	}
}
