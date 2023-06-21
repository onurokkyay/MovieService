package com.krawen.movieservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SearchMovieResponseDTO {
    private Long page;
    @JsonProperty("results")
    private MovieDTO[] movies;
    private Long totalPages;
    private Long totalResults;
}
