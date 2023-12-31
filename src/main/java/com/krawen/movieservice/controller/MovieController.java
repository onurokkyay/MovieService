package com.krawen.movieservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.krawen.movieservice.dto.MovieDetailDTO;
import com.krawen.movieservice.dto.RetrievePersonDetailResponseDTO;
import com.krawen.movieservice.dto.SearchMovieResponseDTO;
import com.krawen.movieservice.dto.SearchPersonResponseDTO;
import com.krawen.movieservice.entity.Genre;
import com.krawen.movieservice.exception.MovieNotFoundException;
import com.krawen.movieservice.external.service.SearchMovieRequest;
import com.krawen.movieservice.service.IMovieService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/movies")
public class MovieController {

	@Autowired
	IMovieService movieService;

	@GetMapping("/{movieId}")
	@Operation(summary = "Retrieve a movie by its ID", description = "Returns the movie details for the given movie ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful retrieval of movie details"),
			@ApiResponse(responseCode = "404", description = "Movie not found") })
	public MovieDetailDTO retrieveMovieById(@PathVariable int movieId) throws MovieNotFoundException {
		return movieService.retrieveMovieById(movieId);
	}

	@GetMapping("")
	@Operation(summary = "Search movies", description = "Searches movies based on the provided criteria")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful retrieval of movie search results"),
			@ApiResponse(responseCode = "400", description = "Invalid request parameters") })
	public SearchMovieResponseDTO searchMovie(@RequestParam String movieName,
			@RequestParam(required = false) boolean includeAdult, @RequestParam(defaultValue = "1") int page) {
		SearchMovieRequest searchMovieRequest = new SearchMovieRequest(movieName, includeAdult, page);
		return movieService.searchMovie(searchMovieRequest);
	}

	@GetMapping("/popular")
	@Operation(summary = "Retrieve popular movies", description = "Retrieve popular pages with page param")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful retrieval of retrieve popular movies results"),
			@ApiResponse(responseCode = "400", description = "Invalid request parameters") })
	public SearchMovieResponseDTO retrievePopularMovies(@RequestParam(defaultValue = "1") int page) {
		return movieService.retrievePopularMovies(page);
	}

	@GetMapping("/genres")
	@Operation(summary = "Retrieve genres", description = "Retrieve genres for movies")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful retrieval of genres results"), })
	public List<Genre> retrieveGenres() {
		return movieService.retrieveGenres();
	}

	/**
	 * The `withGenres` parameter is used to specify the criteria for discovering
	 * movies. It can be a comma (AND) or pipe (OR) separated query.
	 * 
	 * AND/OR Logic Also note that a number of filters support being comma (,) or
	 * pipe (|) separated. Comma's are treated like an AND query while pipe's are
	 * treated like an OR. This allows for quite complex filtering depending on your
	 * desired results. Examples: - "Action,Adventure" --> Retrieve movies of both
	 * Action and Adventure genres (AND operator). - "Drama|Romance" --> Retrieve
	 * movies of either Drama or Romance genre (OR operator). - "Comedy" -->
	 * Retrieve movies of Comedy genre only.
	 * 
	 * The complete list of valid genres will be provided by a specific application
	 * or service (retrieveGenres), and you should use the available genres to
	 * obtain accurate results.
	 * 
	 * @param withGenres A string specifying the genres to be used for movie
	 *                   discovery.
	 * @return A SearchMovieResponseDTO object containing the discovered movies.
	 */
	@GetMapping("/discover")
	@Operation(summary = "Discover movies", description ="""
     Discover movies based on the provided criteria.
     AND/OR Logic: Also note that a number of filters support being comma (,) or
     pipe (|) separated. Comma's are treated like an AND query while pipe's are
     treated like an OR. This allows for quite complex filtering depending on your
     desired results. Examples:
     - "Action,Adventure" --> Retrieve movies of both
     Action and Adventure genres (AND operator).
     - "Drama|Romance" --> Retrieve movies of either Drama or Romance genre (OR operator).
     - "Comedy" --> Retrieve movies of Comedy genre only.
     The complete list of valid genres will be provided by a specific application
     or service (retrieveGenres), and you should use the available genres to
     obtain accurate results.
     """)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful retrieval of discover movie results. "),
			@ApiResponse(responseCode = "400", description = "Invalid request parameters") })
	public SearchMovieResponseDTO discoverMovie(
			@RequestParam(defaultValue = "1") int page,
			@Parameter(name = "withGenres", description = "A string specifying the genres to be used for movie discovery.", example = "Action,Adventure") String withGenres) {
		return movieService.discoverMovie(withGenres,page);
	}
	
	@GetMapping("/person/trending")
	@Operation(summary = "Retrieves a list of trending people based on the specified time window.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of trending people"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
	public SearchPersonResponseDTO retrieveTrendingPeople( 
	@Parameter(description = "Time window for retrieveTrendingPeople. Possible values: 'day', 'week'", schema = @Schema(allowableValues = {"day", "week"}))
    @RequestParam(defaultValue = "day") String timeWindow) {
		return movieService.retrieveTrendingPeople(timeWindow);
	}
	
	@GetMapping("/person/{personId}")
	@Operation(summary = "Retrieve person detail by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of person detail"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
	public RetrievePersonDetailResponseDTO retrievePersonDetailById( 
    @PathVariable int personId) {
		return movieService.retrievePersonDetailById(personId);
	}
}
