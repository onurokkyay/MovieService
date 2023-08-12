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

import com.krawen.movieservice.dto.UserDTO;
import com.krawen.movieservice.exception.UserNotFoundException;
import com.krawen.movieservice.service.IUserService;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
	
	@InjectMocks
	UserController userController;
	
	@Mock
	IUserService userService;
	
	UserDTO userDto;
	String testUserName="testUserName";

	@BeforeEach
	void setup() {
		userDto = new UserDTO();
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
	
}
