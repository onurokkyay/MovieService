package com.krawen.movieservice.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.krawen.movieservice.entity.User;
import com.krawen.movieservice.entity.UserDTO;
import com.krawen.movieservice.exception.UserNameExistException;
import com.krawen.movieservice.exception.UserNotFoundException;
import com.krawen.movieservice.repository.UserRepository;
import com.krawen.movieservice.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	UserRepository userRepo;

	@Override
	public UserDTO retrieveUserByUserName(String userName) throws UserNotFoundException {
		boolean isUserNameExists = userRepo.existsByUserName(userName);
		ModelMapper modelMapper = new ModelMapper();
	    if (!isUserNameExists) {
	        throw new UserNotFoundException(userName);
	    }
		User user = userRepo.findByUserName(userName);
		UserDTO userDTO = modelMapper.map(user, UserDTO.class);
		return userDTO;
	}

	@Override
	public User createUser(UserDTO userDTO) throws UserNameExistException {
	    boolean isUserNameExists = userRepo.existsByUserName(userDTO.getUserName());
	    ModelMapper modelMapper = new ModelMapper();
	    User user = modelMapper.map(userDTO,User.class);

	    if (isUserNameExists) {
	        throw new UserNameExistException(user.getUserName());
	    }
	    user = userRepo.save(user);
	    return user;
		
	}

}
