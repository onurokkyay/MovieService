package com.krawen.movieservice.external.service;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchMovieRequest {
    private String query;
    private boolean includeAdult;
	private int page;
}
