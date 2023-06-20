package com.krawen.movieservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.krawen.movieservice.entity.MovieDetailDTO;
import com.krawen.movieservice.entity.SearchMovieByNameResponseDTO;
import com.krawen.movieservice.external.service.IExternalMovieService;

@RestController
public class MovieController {
	
	@Autowired
	IExternalMovieService extMovieService;

	@GetMapping("/movieservice/movie")
	public MovieDetailDTO retrieveMovie() {
		return extMovieService.retrieveMovie("sa");
	}
	
	@GetMapping("/movieservice/search/{movieName}")
	public SearchMovieByNameResponseDTO searchMovieByName(@PathVariable String movieName) {
		return extMovieService.retrieveMovieByName(movieName);
	}
}
