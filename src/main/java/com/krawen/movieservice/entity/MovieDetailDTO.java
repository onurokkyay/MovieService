package com.krawen.movieservice.entity;

import java.time.LocalDate;

import lombok.Data;

@Data
public class MovieDetailDTO {
	private Boolean adult;
	private String backdropPath;
	private BelongsToCollection belongsToCollection;
	private Long budget;
	private Genre[] genres;
	private String homepage;
	private Long id;
	private String imdbId;
	private String originalLanguage;
	private String originalTitle;
	private String overview;
	private Double popularity;
	private String posterPath;
	private LocalDate releaseDate;
	private Long revenue;
	private Long runtime;
	private String status;
	private String tagline;
	private String title;
	private Boolean video;
	private Double voteAverage;
	private Long voteCount;
}
