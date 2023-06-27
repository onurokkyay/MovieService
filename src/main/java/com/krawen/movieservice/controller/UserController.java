package com.krawen.movieservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.krawen.movieservice.entity.MovieDTO;
import com.krawen.movieservice.entity.User;
import com.krawen.movieservice.entity.UserDTO;
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
	
	@GetMapping("/movieservice/user/{userName}")
	@Operation(summary = "Retrieve a user by username", description = "Returns the user details for the given username")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Successful retrieval of user details"),
	    @ApiResponse(responseCode = "404", description = "User not found")
	})
	public UserDTO retrieveUserByUserName(@PathVariable String userName) throws UserNotFoundException {
		return userService.retrieveUserByUserName(userName);
	}
	
	@PostMapping("/movieservice/user")
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
	 */
	@Operation(summary = "Add a watched movie for a user", description = "This endpoint allows you to add a movie to the watched list of a user.")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Watched movie added"),
	        @ApiResponse(responseCode = "404", description = "User not found")
	})
    @PutMapping("/{userName}/movie/watched")
    public ResponseEntity<?> addWatchedMovie(@PathVariable String userName, @RequestBody MovieDTO watchedMovie) throws UserNotFoundException {
    	userService.addWatchedMovie(userName,watchedMovie);
    	return ResponseEntity.ok("Watched movie added");
    }
	
	/**
	 * Adds a fav movie for a user.
	 *
	 * @param userName  The userName of the user
	 * @param favMovie  The details of the new movie
	 * @return          The HTTP response
	 * @throws UserNotFoundException if the user is not found
	 */
	@Operation(summary = "Add a fav movie for a user", description = "This endpoint allows you to add a movie to the fav list of a user.")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "OK"),
	        @ApiResponse(responseCode = "404", description = "User not found")
	})
    @PutMapping("/{userName}/movie/fav")
    public ResponseEntity<?> addFavMovie(@PathVariable String userName, @RequestBody MovieDTO favMovie) throws UserNotFoundException {
    	userService.addFavMovie(userName,favMovie);
    	return ResponseEntity.ok("Fav movie added");
    }
    
}
