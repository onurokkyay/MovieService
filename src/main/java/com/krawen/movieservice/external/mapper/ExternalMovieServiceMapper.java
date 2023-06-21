package com.krawen.movieservice.external.mapper;

import java.util.Objects;

import org.modelmapper.ModelMapper;

import com.krawen.movieservice.entity.MovieDetail;
import com.krawen.movieservice.entity.MovieDetailDTO;
import com.krawen.movieservice.entity.SearchMovieResponseDTO;
import com.krawen.movieservice.external.service.SearchMovieResponse;

public class ExternalMovieServiceMapper {
	
    //@Autowired
    //private ModelMapper modelMapper;
	
	private final String imagePath = "https://image.tmdb.org/t/p/w500/";
    
    public MovieDetailDTO mapToMovieDetailDTO(MovieDetail movie) {
    	 ModelMapper modelMapper = new ModelMapper();
    	 MovieDetailDTO MovieDetailDTO = modelMapper.map(movie, MovieDetailDTO.class);
    	 if(Objects.nonNull(MovieDetailDTO.getBelongsToCollection())) {
    		 MovieDetailDTO.getBelongsToCollection().setBackdropPath(imagePath.concat(MovieDetailDTO.getBelongsToCollection().getBackdropPath()));
        	 MovieDetailDTO.getBelongsToCollection().setPosterPath(imagePath.concat(MovieDetailDTO.getBelongsToCollection().getPosterPath()));
    	 }
    	 MovieDetailDTO.setBackdropPath(imagePath.concat(movie.getBackdropPath()));
    	 MovieDetailDTO.setPosterPath(imagePath.concat(movie.getPosterPath()));
    	 return MovieDetailDTO;
    }

	public SearchMovieResponseDTO mapToSearchMovieByNameResponseDTO(SearchMovieResponse response) {
		ModelMapper modelMapper = new ModelMapper();
		SearchMovieResponseDTO movie = modelMapper.map(response, SearchMovieResponseDTO.class);
		return movie;
	}
}
