package com.krawen.movieservice.service;

import com.krawen.movieservice.entity.User;
import com.krawen.movieservice.entity.UserDTO;
import com.krawen.movieservice.exception.UserNameExistException;
import com.krawen.movieservice.exception.UserNotFoundException;

public interface IUserService {
	UserDTO retrieveUserByUserName(String userName) throws UserNotFoundException;
	User createUser(UserDTO user) throws UserNameExistException;
}
