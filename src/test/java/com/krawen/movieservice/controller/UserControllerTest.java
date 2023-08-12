package com.krawen.movieservice.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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

import com.krawen.movieservice.dto.UserDTO;
import com.krawen.movieservice.entity.User;
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

	@BeforeEach
	void setup() {
		userDto = new UserDTO();
		user = new User();
		user.setId("1");
		userDto.setUserName(testUserName);
	}

	@Test
	public void testRetrieveUserByUserNameSuccess() throws UserNotFoundException {
		when(userService.retrieveUserByUserName(any(String.class))).thenReturn(userDto);
		assertEquals(testUserName, userController.retrieveUserByUserName(testUserName).getUserName());
	}

	@Test
	public void testRetrieveUserByUserNameUserNotFoundException() throws UserNotFoundException {
		when(userService.retrieveUserByUserName(any(String.class))).thenThrow(UserNotFoundException.class);
		assertThrows(UserNotFoundException.class, () -> userController.retrieveUserByUserName(testUserName));
	}

	@Test
	public void testCreateUserSuccess() throws UserNameExistException {
		when(userService.createUser(userDto)).thenReturn(user);
		assertEquals(HttpStatus.OK.value(), userController.createUser(userDto).getStatusCode().value());
	}

	@Test
	public void testCreateUserUserNameExistException() throws UserNameExistException {
		when(userService.createUser(userDto)).thenThrow(UserNameExistException.class);
		assertThrows(UserNameExistException.class, ()-> userController.createUser(userDto));
	}

}
