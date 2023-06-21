package com.krawen.movieservice.external.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.krawen.movieservice.entity.Movie;

import lombok.Data;

@Data
public class SearchMovieResponse {
    private Long page;
    @JsonProperty("results")
    private Movie[] movies;
    @JsonProperty("total_pages")
    private Long totalPages;
    @JsonProperty("total_results")
    private Long totalResults;
}
