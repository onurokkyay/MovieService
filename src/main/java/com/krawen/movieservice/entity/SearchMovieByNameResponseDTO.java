package com.krawen.movieservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.krawen.movieservice.entity.Movie;

import lombok.Data;

@Data
public class SearchMovieByNameResponseDTO {
    private Long page;
    @JsonProperty("results")
    private MovieDTO[] movies;
    private Long totalPages;
    private Long totalResults;
}
