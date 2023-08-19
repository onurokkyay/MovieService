package com.krawen.movieservice.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SearchMovieResponseDTO {
    private Long page;
    private final List<MovieDTO> movies = new ArrayList<MovieDTO>();;
    private Long totalPages;
    private Long totalResults;
}
