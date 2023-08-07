package com.krawen.movieservice.dto;

import java.util.List;

import com.krawen.movieservice.entity.Movie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

	private String userName;
	private String mail;
	private List<Movie> watchedMovies;
	private List<Movie> favMovies;

}
