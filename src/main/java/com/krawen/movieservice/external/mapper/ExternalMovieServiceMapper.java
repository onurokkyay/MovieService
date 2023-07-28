package com.krawen.movieservice.external.mapper;

import java.util.Objects;

import org.modelmapper.ModelMapper;

import com.krawen.movieservice.entity.MovieDetail;
import com.krawen.movieservice.entity.MovieDetailDTO;
import com.krawen.movieservice.entity.SearchMovieResponseDTO;
import com.krawen.movieservice.external.service.SearchMovieResponse;

import io.micrometer.common.util.StringUtils;

public class ExternalMovieServiceMapper {

	// @Autowired
	// private ModelMapper modelMapper;

	private final String imagePath = "https://image.tmdb.org/t/p/w500/";

	public MovieDetailDTO mapToMovieDetailDTO(MovieDetail movie) {
		ModelMapper modelMapper = new ModelMapper();
		MovieDetailDTO MovieDetailDTO = modelMapper.map(movie, MovieDetailDTO.class);
		if (Objects.nonNull(MovieDetailDTO.getBelongsToCollection())) {
			MovieDetailDTO.getBelongsToCollection()
					.setBackdropPath(addImagePath(MovieDetailDTO.getBelongsToCollection().getBackdropPath()));
			MovieDetailDTO.getBelongsToCollection()
					.setPosterPath(addImagePath(MovieDetailDTO.getBelongsToCollection().getPosterPath()));
		}
		MovieDetailDTO.setBackdropPath(addImagePath(movie.getBackdropPath()));
		MovieDetailDTO.setPosterPath(addImagePath(movie.getPosterPath()));
		return MovieDetailDTO;
	}

	public SearchMovieResponseDTO mapToSearchMovieByNameResponseDTO(SearchMovieResponse response) {
		ModelMapper modelMapper = new ModelMapper();
		SearchMovieResponseDTO searchMovie = modelMapper.map(response, SearchMovieResponseDTO.class);
		searchMovie.getMovies().stream().forEach(movie -> {
			movie.setBackdropPath(addImagePath(movie.getBackdropPath()));
			movie.setPosterPath(addImagePath(movie.getPosterPath()));
		});
		return searchMovie;
	}

	private String addImagePath(String path) {
		if (Objects.isNull(path) && StringUtils.isEmpty(path)) {
			return path;
		} else {
			return imagePath.concat(path);
		}

	}

}
