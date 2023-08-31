package com.krawen.movieservice.external.mapper;

import java.util.Objects;

import org.modelmapper.ModelMapper;

import com.krawen.movieservice.dto.MovieDetailDTO;
import com.krawen.movieservice.dto.RetrievePersonDetailResponseDTO;
import com.krawen.movieservice.dto.SearchMovieResponseDTO;
import com.krawen.movieservice.dto.SearchPersonResponseDTO;
import com.krawen.movieservice.entity.MovieDetail;
import com.krawen.movieservice.external.service.SearchMovieResponse;
import com.krawen.movieservice.external.service.dto.RetrievePersonDetailResponse;
import com.krawen.movieservice.external.service.dto.SearchPersonResponse;

import io.micrometer.common.util.StringUtils;

public class ExternalMovieServiceMapper {

	// @Autowired
	// private ModelMapper modelMapper;

	private static final String IMAGE_PATH = "https://image.tmdb.org/t/p/w500/";

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

	public SearchPersonResponseDTO mapToSearchPeopleResponseDTO(SearchPersonResponse response) {
		ModelMapper modelMapper = new ModelMapper();
		SearchPersonResponseDTO searchPersonResponseDto = modelMapper.map(response, SearchPersonResponseDTO.class);
		searchPersonResponseDto.getPersonList().stream().forEach(person -> {
			person.setProfilePath(addImagePath(person.getProfilePath()));
		});
		searchPersonResponseDto.getPersonList().stream().forEach(person -> {
			person.getMovieList().stream().forEach(movie -> {
				movie.setBackdropPath(addImagePath(movie.getBackdropPath()));
				movie.setPosterPath(addImagePath(movie.getPosterPath()));
			});
		});
		return searchPersonResponseDto;
	}
	
	public RetrievePersonDetailResponseDTO mapToRetrievePersonDetailResponseDTO(RetrievePersonDetailResponse response) {
		ModelMapper modelMapper = new ModelMapper();
		RetrievePersonDetailResponseDTO retrievePersonDetailResponseDto = modelMapper.map(response, RetrievePersonDetailResponseDTO.class);
		retrievePersonDetailResponseDto.setProfilePath(addImagePath(retrievePersonDetailResponseDto.getProfilePath()));
		return retrievePersonDetailResponseDto;
	}

	private String addImagePath(String path) {
		if (Objects.isNull(path) && StringUtils.isEmpty(path)) {
			return path;
		} else {
			return IMAGE_PATH.concat(path);
		}

	}

}
