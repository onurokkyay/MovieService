package com.krawen.movieservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.krawen.movieservice.entity.User;
import com.krawen.movieservice.service.IUserService;

@RestController
public class UserController {
	
	@Autowired
	IUserService userService;
	
	@GetMapping("/movieservice/user/{userName}")
	public User retrieveUserByUserName(@PathVariable String userName) {
		return userService.retrieveUserByUserName(userName);
	}
	
}
