package com.krawen.movieservice.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MovieDTO {
    private Boolean adult;
    private String backdropPath;
    private long[] genreIDS;
    private Long id;
    private String originalLanguage;
    private String originalTitle;
    private String overview;
    private Double popularity;
    private String posterPath;
    private LocalDate releaseDate;
    private String title;
    private Boolean video;
    private Double voteAverage;
    private Long voteCount;
}
