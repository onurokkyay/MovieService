package com.krawen.movieservice.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import com.krawen.movieservice.dto.AddMovieDTO;
import com.krawen.movieservice.dto.UserDTO;
import com.krawen.movieservice.entity.User;
import com.krawen.movieservice.exception.MovieAlreadyExistException;
import com.krawen.movieservice.exception.MovieNotFoundException;
import com.krawen.movieservice.exception.UserNameExistException;
import com.krawen.movieservice.exception.UserNotFoundException;
import com.krawen.movieservice.service.IUserService;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

	@InjectMocks
	UserController userController;

	@Mock
	IUserService userService;

	UserDTO userDto;
	User user;
	String testUserName = "testUserName";
	AddMovieDTO addMovieDto;
	int movieId = 1;
	String userId = "1";

	@BeforeEach
	void setup() {
		userDto = new UserDTO();
		user = new User();
		user.setId(userId);
		userDto.setUserName(testUserName);
		addMovieDto = new AddMovieDTO();
		addMovieDto.setId(1);
	}

	@Test
	void testRetrieveUserByUserNameSuccess() throws UserNotFoundException {
		when(userService.retrieveUserByUserName(any(String.class))).thenReturn(userDto);
		assertEquals(testUserName, userController.retrieveUserByUserName(testUserName).getUserName());
	}

	@Test
	void testRetrieveUserByUserNameUserNotFoundException() throws UserNotFoundException {
		when(userService.retrieveUserByUserName(any(String.class))).thenThrow(UserNotFoundException.class);
		assertThrows(UserNotFoundException.class, () -> userController.retrieveUserByUserName(testUserName));
	}

	@Test
	void testCreateUserSuccess() throws UserNameExistException {
		when(userService.createUser(userDto)).thenReturn(user);
		assertEquals(HttpStatus.OK.value(), userController.createUser(userDto).getStatusCode().value());
	}

	@Test
	void testCreateUserUserNameExistException() throws UserNameExistException {
		when(userService.createUser(userDto)).thenThrow(UserNameExistException.class);
		assertThrows(UserNameExistException.class, ()-> userController.createUser(userDto));
	}

	@Test
	void testAddWatchedMovieSuccess()
			throws UserNotFoundException, MovieNotFoundException, MovieAlreadyExistException {
		doNothing().when(userService).addWatchedMovie(testUserName, movieId);
		assertEquals(HttpStatus.OK.value(),
				userController.addWatchedMovie(testUserName, addMovieDto).getStatusCode().value());
	}

	@Test
	void testAddWatchedMovieMovieNotFoundException()
			throws UserNotFoundException, MovieNotFoundException, MovieAlreadyExistException {
		doThrow(new MovieNotFoundException(movieId)).when(userService).addWatchedMovie(testUserName, movieId);
		assertThrows(MovieNotFoundException.class, () -> userController.addWatchedMovie(testUserName, addMovieDto));
	}

	@Test
	void testAddWatchedMovieUserNotFoundException()
			throws UserNotFoundException, MovieNotFoundException, MovieAlreadyExistException {
		doThrow(new UserNotFoundException(testUserName)).when(userService).addWatchedMovie(testUserName, movieId);
		assertThrows(UserNotFoundException.class, () -> userController.addWatchedMovie(testUserName, addMovieDto));
	}
	
	@Test
	void testAddFavMovieSuccess()
			throws UserNotFoundException, MovieNotFoundException, MovieAlreadyExistException {
		doNothing().when(userService).addFavMovie(testUserName, movieId);
		assertEquals(HttpStatus.OK.value(),
				userController.addFavMovie(testUserName, addMovieDto).getStatusCode().value());
	}

	@Test
	void testAddFavMovieMovieNotFoundException()
			throws UserNotFoundException, MovieNotFoundException, MovieAlreadyExistException {
		doThrow(new MovieNotFoundException(movieId)).when(userService).addFavMovie(testUserName, movieId);
		assertThrows(MovieNotFoundException.class, () -> userController.addFavMovie(testUserName, addMovieDto));
	}

	@Test
	void testAddFavMovieUserNotFoundException()
			throws UserNotFoundException, MovieNotFoundException, MovieAlreadyExistException {
		doThrow(new UserNotFoundException(testUserName)).when(userService).addFavMovie(testUserName, movieId);
		assertThrows(UserNotFoundException.class, () -> userController.addFavMovie(testUserName, addMovieDto));
	}
	
	@Test
	void testRemoveMovieFromWatchedMovies() throws UserNotFoundException, MovieNotFoundException {
		doNothing().when(userService).removeWatchedMovie(testUserName, movieId);
		assertEquals(HttpStatus.OK.value(),
				userController.removeMovieFromWatchedMovies(testUserName, movieId).getStatusCode().value());		
	}
	
	@Test
	void testRemoveMovieFromWatchedMoviesUserNotFoundException() throws UserNotFoundException, MovieNotFoundException {
		doThrow(new UserNotFoundException(testUserName)).when(userService).removeWatchedMovie(testUserName, movieId);
		assertThrows(UserNotFoundException.class, () -> userController.removeMovieFromWatchedMovies(testUserName, movieId));
	}
	
	@Test
	void testRemoveMovieFromWatchedMoviesMovieNotFoundException() throws UserNotFoundException, MovieNotFoundException {
		doThrow(new MovieNotFoundException(movieId)).when(userService).removeWatchedMovie(testUserName, movieId);
		assertThrows(MovieNotFoundException.class, () -> userController.removeMovieFromWatchedMovies(testUserName, movieId));
	}
	
	@Test
	void testRemoveMovieFromFavMovies() throws UserNotFoundException, MovieNotFoundException {
		doNothing().when(userService).removeFavMovie(testUserName, movieId);
		assertEquals(HttpStatus.OK.value(),
				userController.removeMovieFromFavMovies(testUserName, movieId).getStatusCode().value());		
	}
	
	@Test
	void testRemoveMovieFromFavMoviesUserNotFoundException() throws UserNotFoundException, MovieNotFoundException {
		doThrow(new UserNotFoundException(testUserName)).when(userService).removeFavMovie(testUserName, movieId);
		assertThrows(UserNotFoundException.class, () -> userController.removeMovieFromFavMovies(testUserName, movieId));
	}
	
	@Test
	void testRemoveMovieFromFavMoviesMovieNotFoundException() throws UserNotFoundException, MovieNotFoundException {
		doThrow(new MovieNotFoundException(movieId)).when(userService).removeFavMovie(testUserName, movieId);
		assertThrows(MovieNotFoundException.class, () -> userController.removeMovieFromFavMovies(testUserName, movieId));
	}

}
