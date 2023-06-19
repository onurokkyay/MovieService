package com.krawen.movieservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.krawen.movieservice.entity.User;
import com.krawen.movieservice.repository.UserRepository;
import com.krawen.movieservice.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	UserRepository userRepo;

	@Override
	public User retrieveUserByUserName(String userName) {
		return userRepo.findByUserName(userName);
	}

}
