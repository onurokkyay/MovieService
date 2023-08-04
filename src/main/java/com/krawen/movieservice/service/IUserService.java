package com.krawen.movieservice.service;

import com.krawen.movieservice.dto.UserDTO;
import com.krawen.movieservice.entity.User;
import com.krawen.movieservice.exception.MovieNotFoundException;
import com.krawen.movieservice.exception.UserNameExistException;
import com.krawen.movieservice.exception.UserNotFoundException;

public interface IUserService {
	UserDTO retrieveUserByUserName(String userName) throws UserNotFoundException;
	User createUser(UserDTO user) throws UserNameExistException;
	void addWatchedMovie(String userName, int movieId) throws UserNotFoundException, MovieNotFoundException;
	void addFavMovie(String userName, int movieId) throws UserNotFoundException, MovieNotFoundException;
	void removeWatchedMovie(String userName, String watchedMovieName) throws UserNotFoundException, MovieNotFoundException;
	void removeFavMovie(String userName, String favMovieName) throws UserNotFoundException, MovieNotFoundException;
}
	