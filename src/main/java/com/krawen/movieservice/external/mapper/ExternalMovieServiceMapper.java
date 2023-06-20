package com.krawen.movieservice.external.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import com.krawen.movieservice.entity.Movie;
import com.krawen.movieservice.entity.MovieDTO;
import com.krawen.movieservice.entity.MovieDetail;
import com.krawen.movieservice.entity.MovieDetailDTO;
import com.krawen.movieservice.entity.SearchMovieByNameResponseDTO;
import com.krawen.movieservice.external.service.SearchMovieByNameResponse;

public class ExternalMovieServiceMapper {
	
    //@Autowired
    //private ModelMapper modelMapper;
	
	private final String imagePath = "https://image.tmdb.org/t/p/w500/";
    
    public MovieDetailDTO mapToMovieDetailDTO(MovieDetail movie) {
    	 ModelMapper modelMapper = new ModelMapper();
    	 MovieDetailDTO MovieDetailDTO = modelMapper.map(movie, MovieDetailDTO.class);
    	 MovieDetailDTO.getBelongsToCollection().setBackdropPath(imagePath.concat(MovieDetailDTO.getBelongsToCollection().getBackdropPath()));
    	 MovieDetailDTO.getBelongsToCollection().setPosterPath(imagePath.concat(MovieDetailDTO.getBelongsToCollection().getPosterPath()));
    	 MovieDetailDTO.setBackdropPath(imagePath.concat(movie.getBackdropPath()));
    	 MovieDetailDTO.setPosterPath(imagePath.concat(movie.getPosterPath()));
    	 return MovieDetailDTO;
    }

	public SearchMovieByNameResponseDTO mapToSearchMovieByNameResponseDTO(SearchMovieByNameResponse response) {
		ModelMapper modelMapper = new ModelMapper();

	    TypeMap<SearchMovieByNameResponse, SearchMovieByNameResponseDTO> propertyMapper = modelMapper.createTypeMap(SearchMovieByNameResponse.class, SearchMovieByNameResponseDTO.class);
	    //propertyMapper.addMapping(SearchMovieByNameResponse::getMovies, SearchMovieByNameResponseDTO::getMovies);
		SearchMovieByNameResponseDTO movie = modelMapper.map(response, SearchMovieByNameResponseDTO.class);
		return movie;
	}
}
