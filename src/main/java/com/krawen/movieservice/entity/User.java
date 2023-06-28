package com.krawen.movieservice.entity;

import java.util.List;
import java.util.stream.Collectors;
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
	
	public void removeWatchedMovieByName (String watchedMovieName) {
		setWatchedMovies(removeMovie(watchedMovieName,getFavMovies()));
	}
	
	public void removeFavMovieByName (String favMovieName) {
		setFavMovies(removeMovie(favMovieName,getFavMovies()));
	}
	
	private List<Movie> removeMovie(String watchedMovieName, List<Movie> movieList) {
		return movieList.stream()
		.filter(movie -> !movie.getTitle().equals(watchedMovieName))
		.collect(Collectors.toList());
	}

}
