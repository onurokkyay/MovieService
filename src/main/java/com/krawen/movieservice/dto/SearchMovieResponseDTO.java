package com.krawen.movieservice.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SearchMovieResponseDTO {
    private Long page;
    private List<MovieDTO> movies;
    private Long totalPages;
    private Long totalResults;
    
	public List<MovieDTO> getMovies() {
		if (movies == null) {
			movies = new ArrayList<MovieDTO>();
		}
		return movies;
	}

}
