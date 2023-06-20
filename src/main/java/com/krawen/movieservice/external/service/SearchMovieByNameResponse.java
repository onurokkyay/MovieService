package com.krawen.movieservice.external.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.krawen.movieservice.entity.Movie;

import lombok.Data;

@Data
public class SearchMovieByNameResponse {
    private Long page;
    @JsonProperty("results")
    private Movie[] movies;
    private Long totalPages;
    private Long totalResults;
}
