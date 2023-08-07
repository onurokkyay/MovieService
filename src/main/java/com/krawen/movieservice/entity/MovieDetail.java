package com.krawen.movieservice.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MovieDetail {
	private Boolean adult;
	@JsonProperty("backdrop_path")
	private String backdropPath;
	@JsonProperty("belongs_to_collection")
	private BelongsToCollection belongsToCollection;
	private Long budget;
	private Genre[] genres;
	private String homepage;
	private int id;
	@JsonProperty("imdb_id")
	private String imdbId;
	@JsonProperty("original_language")
	private String originalLanguage;
	@JsonProperty("original_title")
	private String originalTitle;
	private String overview;
	private Double popularity;
	@JsonProperty("poster_path")
	private String posterPath;
	@JsonProperty("release_date")
	private LocalDate releaseDate;
	private Long revenue;
	private Long runtime;
	private String status;
	private String tagline;
	private String title;
	private Boolean video;
	@JsonProperty("vote_average")
	private Double voteAverage;
	@JsonProperty("vote_count")
	private Long voteCount;
}
