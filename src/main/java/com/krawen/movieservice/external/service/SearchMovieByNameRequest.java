package com.krawen.movieservice.external.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.krawen.movieservice.entity.Movie;

import lombok.Data;

@Data
public class SearchMovieByNameRequest {
    private String query;
}
