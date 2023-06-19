package com.krawen.movieservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.krawen.movieservice.entity.Movie;
import com.krawen.movieservice.service.IExternalMovieService;

public class ExternalMovieServiceImpl implements IExternalMovieService {
	
	@Autowired
	RestTemplate restTemplate;

	@Override
	public Movie retrieveMovie(String movieName) {
		restTemplate.getForEntity("url", null);
		return null;
	}

}
