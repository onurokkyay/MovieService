package com.krawen.movieservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.krawen.movieservice.entity.MovieDetailDTO;
import com.krawen.movieservice.entity.SearchMovieResponseDTO;
import com.krawen.movieservice.external.service.IExternalMovieService;
import com.krawen.movieservice.external.service.SearchMovieRequest;
import com.krawen.movieservice.service.IMovieService;

@Service
public class MovieServiceImpl implements IMovieService {
	
	@Autowired
	IExternalMovieService extMovieService;

	@Override
	public MovieDetailDTO retrieveMovie(String movieName) {
		return extMovieService.retrieveMovie(movieName);
	}

	@Override
	public SearchMovieResponseDTO searchMovie(SearchMovieRequest request) {
		return extMovieService.searchMovie(request);
	}

}
