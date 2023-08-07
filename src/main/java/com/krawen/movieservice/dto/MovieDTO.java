package com.krawen.movieservice.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class MovieDTO {
    private Boolean adult;
    private String backdropPath;
    private long[] genreIdList;
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
