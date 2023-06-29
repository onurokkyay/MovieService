package com.krawen.movieservice.service;

import com.krawen.movieservice.entity.MovieDTO;
import com.krawen.movieservice.entity.User;
import com.krawen.movieservice.entity.UserDTO;
import com.krawen.movieservice.exception.UserNameExistException;
import com.krawen.movieservice.exception.UserNotFoundException;

public interface IUserService {
	UserDTO retrieveUserByUserName(String userName) throws UserNotFoundException;
	User createUser(UserDTO user) throws UserNameExistException;
	void addWatchedMovie(String userName, MovieDTO watchedMovie) throws UserNotFoundException;
	void addFavMovie(String userName, MovieDTO favMovie) throws UserNotFoundException;
	void removeWatchedMovie(String userName, String watchedMovieName) throws UserNotFoundException;
	void removeFavMovie(String userName, String favMovieName) throws UserNotFoundException;
}
