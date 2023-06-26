package com.krawen.movieservice.entity;

import java.util.List;

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
