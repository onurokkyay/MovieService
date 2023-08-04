package com.krawen.movieservice.entity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

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
	
	public void removeWatchedMovieByName (String watchedMovieName) throws MovieNotFoundException {
		setWatchedMovies(removeMovie(watchedMovieName,getFavMovies()));
	}
	
	public void removeFavMovieByName (String favMovieName) throws MovieNotFoundException {
		setFavMovies(removeMovie(favMovieName,getFavMovies()));
	}
	
	private List<MovieDetail> removeMovie(String movieName, List<MovieDetail> movieList) throws MovieNotFoundException {
		if(null == movieList.stream().filter(movie -> movie.getTitle().equals(movieName)).findAny().orElse(null)) {
			throw new MovieNotFoundException(movieName);
		}
		return movieList.stream()
		.filter(movie -> !movie.getTitle().equals(movieName))
		.collect(Collectors.toList());
	}

}
