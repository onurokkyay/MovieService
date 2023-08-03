package com.krawen.movieservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.krawen.movieservice.entity.User;
import com.krawen.movieservice.entity.UserDTO;
import com.krawen.movieservice.exception.UserNameExistException;
import com.krawen.movieservice.exception.UserNotFoundException;
import com.krawen.movieservice.kafka.UserKafkaProducer;
import com.krawen.movieservice.repository.UserRepository;


class UserServiceImplTest {
	
    private UserRepository userRepo;
    private UserKafkaProducer userKafkaProducer;
    private UserServiceImpl userService;
	private final String testUsername = "test";
    UserDTO userDTO;
    User savedUser;
    User user = new User();

	
    @BeforeEach
    public void setup() {
        userRepo = mock(UserRepository.class);
        userKafkaProducer = mock(UserKafkaProducer.class);
        userService = new UserServiceImpl(userRepo, userKafkaProducer);
        userDTO = new UserDTO();
        userDTO.setUserName(testUsername);
        savedUser = new User();
        savedUser.setUserName(testUsername);
        savedUser.setId("1");
        user = new User();
        user.setUserName(testUsername);
    }

	 @Test
	    public void testCreateUserSuccess() throws UserNameExistException {

	        when(userRepo.existsByUserName(userDTO.getUserName())).thenReturn(false);

	        when(userRepo.save(any(User.class))).thenReturn(savedUser);

	        User user = userService.createUser(userDTO);

	        assertNotNull(user);
	        assertNotNull(user.getId());
	        assertEquals(userDTO.getUserName(), user.getUserName());
	        verify(userRepo, times(1)).existsByUserName(userDTO.getUserName());
	        verify(userRepo, times(1)).save(any(User.class));
	        verify(userKafkaProducer, times(1)).sendMessage(userDTO); 

	    }

	    @Test()
	    public void testCreateUserUserNameExists() throws UserNameExistException {

	        when(userRepo.existsByUserName(userDTO.getUserName())).thenReturn(true);

	        assertThrows(UserNameExistException.class, () -> userService.createUser(userDTO));
	        verify(userRepo, times(1)).existsByUserName(userDTO.getUserName());
	        verify(userRepo, times(0)).save(any(User.class));
	        verify(userKafkaProducer, times(0)).sendMessage(any(UserDTO.class));
	    }
	    
		 @Test
		    public void testRetrieveUserSuccess() throws UserNotFoundException {

		        when(userRepo.existsByUserName(testUsername)).thenReturn(true);

		        when(userRepo.findByUserName(any(String.class))).thenReturn(user);

		        UserDTO userDTO = userService.retrieveUserByUserName(testUsername);

		        assertNotNull(userDTO);
		        assertEquals(userDTO.getUserName(), user.getUserName());
		        verify(userRepo, times(1)).existsByUserName(userDTO.getUserName());
		        verify(userRepo, times(1)).findByUserName(testUsername);

		    }

		    @Test
		    public void testRetrieveUserUserNotFound() throws UserNotFoundException {

		        when(userRepo.existsByUserName(userDTO.getUserName())).thenReturn(false);

		        assertThrows(UserNotFoundException.class, () -> userService.retrieveUserByUserName(userDTO.getUserName()));
		        verify(userRepo, times(1)).existsByUserName(userDTO.getUserName());
		        verify(userRepo, times(0)).findByUserName(any(String.class));

		    }

}
