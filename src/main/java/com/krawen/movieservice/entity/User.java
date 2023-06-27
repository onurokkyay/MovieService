package com.krawen.movieservice.entity;

import java.util.List;
import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document ("movieUsers")
public class User {
	@Id
	private String id;
	private String userName;
	private String mail;
	private List<Movie> watchedMovies;
	private List<Movie> favMovies;
	
	public List<Movie> getWatchedMovies() {
		if (watchedMovies == null) {
			watchedMovies = new ArrayList<>();
		}
		return watchedMovies;
	}
	
	public List<Movie> getFavMovies() {
		if (favMovies == null) {
			favMovies = new ArrayList<>();
		}
		return favMovies;
	}

}
