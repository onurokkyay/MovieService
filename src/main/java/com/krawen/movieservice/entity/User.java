package com.krawen.movieservice.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.krawen.movieservice.exception.MovieNotFoundException;

import lombok.Data;

@Data
@Document ("movieUsers")
public class User {
	@Id
	private String id;
	private String userName;
	private String mail;
	private List<MovieDetail> watchedMovies;
	private List<MovieDetail> favMovies;
	
	public List<MovieDetail> getWatchedMovies() {
		if (watchedMovies == null) {
			watchedMovies = new ArrayList<>();
		}
		return watchedMovies;
	}
	
	public List<MovieDetail> getFavMovies() {
		if (favMovies == null) {
			favMovies = new ArrayList<>();
		}
		return favMovies;
	}
	
	public void removeWatchedMovieByName (int id) throws MovieNotFoundException {
		setWatchedMovies(removeMovie(id,getWatchedMovies()));
	}
	
	public void removeFavMovieByName (int id) throws MovieNotFoundException {
		setFavMovies(removeMovie(id,getFavMovies()));
	}
	
	private List<MovieDetail> removeMovie(int id, List<MovieDetail> movieList) throws MovieNotFoundException {
		if(null == movieList.stream().filter(movie -> movie.getId() == id).findAny().orElse(null)) {
			throw new MovieNotFoundException(id);
		}
		return movieList.stream()
		.filter(movie -> !(movie.getId() == id))
		.collect(Collectors.toList());
	}

}
