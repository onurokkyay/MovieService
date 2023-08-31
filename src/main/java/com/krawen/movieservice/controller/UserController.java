package com.krawen.movieservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.krawen.movieservice.aspect.Log;
import com.krawen.movieservice.dto.AddMovieDTO;
import com.krawen.movieservice.dto.UserDTO;
import com.krawen.movieservice.entity.User;
import com.krawen.movieservice.exception.MovieAlreadyExistException;
import com.krawen.movieservice.exception.MovieNotFoundException;
import com.krawen.movieservice.exception.UserNameExistException;
import com.krawen.movieservice.exception.UserNotFoundException;
import com.krawen.movieservice.service.IUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class UserController {
	
	@Autowired
	IUserService userService;
	
	@GetMapping("/users/{userName}")
	@Operation(summary = "Retrieve a user by username", description = "Returns the user details for the given username")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Successful retrieval of user details"),
	    @ApiResponse(responseCode = "404", description = "User not found")
	})
	public UserDTO retrieveUserByUserName(@PathVariable String userName) throws UserNotFoundException {
		return userService.retrieveUserByUserName(userName);
	}
	
	@Log
	@PostMapping("/users")
	@Operation(summary = "Create a new user", description = "Creates a new user with the provided user information")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "User created successfully"),
	        @ApiResponse(responseCode = "409", description = "Conflict - Username already exists")
	})
	public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) throws UserNameExistException {
		User user = userService.createUser(userDTO);
		return ResponseEntity.ok("User Created id:"+user.getId());
	}
	
	/**
	 * Adds a watched movie for a user.
	 *
	 * @param userName  The userName of the user
	 * @param watchedMovie  The details of the new movie
	 * @return          The HTTP response
	 * @throws UserNotFoundException if the user is not found
	 * @throws MovieNotFoundException 
	 * @throws MovieAlreadyExistException 
	 */
	@Operation(summary = "Add a watched movie for a user", description = "This endpoint allows you to add a movie to the watched list of a user.")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Watched movie added"),
	        @ApiResponse(responseCode = "404", description = "User not found")
	})
    @PutMapping("/users/{userName}/movies/watched")
    public ResponseEntity<?> addWatchedMovie(@PathVariable String userName, @RequestBody AddMovieDTO movie) throws UserNotFoundException, MovieNotFoundException, MovieAlreadyExistException {
    	userService.addWatchedMovie(userName,movie.getId());
    	return ResponseEntity.ok("Watched movie added");
    }
	
	/**
	 * Adds a fav movie for a user.
	 *
	 * @param userName  The userName of the user
	 * @param favMovie  The details of the new movie
	 * @return          The HTTP response
	 * @throws UserNotFoundException if the user is not found
	 * @throws MovieNotFoundException 
	 * @throws MovieAlreadyExistException 
	 */
	@Operation(summary = "Add a fav movie for a user", description = "This endpoint allows you to add a movie to the fav list of a user.")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "OK"),
	        @ApiResponse(responseCode = "404", description = "User not found")
	})
    @PutMapping("/users/{userName}/movies/favorites")
    public ResponseEntity<?> addFavMovie(@PathVariable String userName, @RequestBody AddMovieDTO movie) throws UserNotFoundException, MovieNotFoundException, MovieAlreadyExistException {
    	userService.addFavMovie(userName,movie.getId());
    	return ResponseEntity.ok("Fav movie added");
    }
	
	/**
	 * Remove a movie from the watched movies list of a user.
	 *
	 * @param userName   User name
	 * @param movieName  Movie name
	 * @return HTTP response indicating success
	 * @throws UserNotFoundException if the user is not found
	 * @throws MovieNotFoundException 
	 */
	@Operation(summary = "Remove a movie from the watched movies list", description = "This endpoint allows you to remove a movie from the watched movies list of a user.")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Watched movie deleted"),
	        @ApiResponse(responseCode = "404", description = "User not found")
	})
	@DeleteMapping("/users/{userName}/movies/watched/{id}")
	public ResponseEntity<?> removeMovieFromWatchedMovies(
	        @PathVariable("userName") String userName,
	        @PathVariable("id") int id) throws UserNotFoundException, MovieNotFoundException {
	    userService.removeWatchedMovie(userName, id);
	    return ResponseEntity.ok("Watched movie deleted");
	}
    
	/**
	 * Remove a movie from the fav movies list of a user.
	 *
	 * @param userName   User name
	 * @param movieName  Movie name
	 * @return HTTP response indicating success
	 * @throws UserNotFoundException if the user is not found
	 * @throws MovieNotFoundException 
	 */
	@Operation(summary = "Remove a movie from the fav movies list", description = "This endpoint allows you to remove a movie from the fav movies list of a user.")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Watched movie deleted"),
	        @ApiResponse(responseCode = "404", description = "User not found")
	})
    @DeleteMapping("/users/{userName}/movies/favorites/{id}")
    public ResponseEntity<?>  removeMovieFromFavMovies(
            @PathVariable("userName") String userName,
            @PathVariable("id") int id) throws UserNotFoundException, MovieNotFoundException {
        userService.removeFavMovie(userName, id);
        return ResponseEntity.ok("Fav movie deleted");
    }
    
}
