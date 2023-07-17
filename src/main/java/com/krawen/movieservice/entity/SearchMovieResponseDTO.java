package com.krawen.movieservice.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SearchMovieResponseDTO {
    private Long page;
    @JsonProperty("results")
    private List<MovieDTO> movies;
    private Long totalPages;
    private Long totalResults;
}
