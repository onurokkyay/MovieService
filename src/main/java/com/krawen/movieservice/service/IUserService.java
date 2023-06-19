package com.krawen.movieservice.service;

import com.krawen.movieservice.entity.User;

public interface IUserService {
	User retrieveUserByUserName(String userName);
}
