package com.krawen.movieservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.krawen.movieservice.entity.User;
import com.krawen.movieservice.service.IUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

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
	public User retrieveUserByUserName(@PathVariable String userName) {
		return userService.retrieveUserByUserName(userName);
	}
	
}
