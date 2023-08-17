package com.krawen.movieservice.external.service.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.krawen.movieservice.entity.Movie;

import lombok.Data;

@Data
public class Person {
    private boolean adult;
    private long id;
    private String name;
    @JsonProperty("original_name")
    private String originalName;
    @JsonProperty("media_type")
    private String mediaType;
    private double popularity;
    private long gender;
    @JsonProperty("known_for_department")
    private String knownForDepartment;
    @JsonProperty("profile_path")
    private String profilePath;
    @JsonProperty("known_for")
    private List<Movie> movieList;
}
