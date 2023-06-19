package com.krawen.movieservice.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document ("movieUsers")
public class User {
	@Id
	private String id;
	private String userName;
	private List<Movie> watchedMovies;
	private List<Movie> favMovies;

}
