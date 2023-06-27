package com.krawen.movieservice.service.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.krawen.movieservice.entity.Movie;
import com.krawen.movieservice.entity.MovieDTO;
import com.krawen.movieservice.entity.User;
import com.krawen.movieservice.entity.UserDTO;
import com.krawen.movieservice.exception.UserNameExistException;
import com.krawen.movieservice.exception.UserNotFoundException;
import com.krawen.movieservice.kafka.UserKafkaProducer;
import com.krawen.movieservice.repository.UserRepository;
import com.krawen.movieservice.service.IUserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	UserKafkaProducer userKafkaProducer;

	@Override
	public UserDTO retrieveUserByUserName(String userName) throws UserNotFoundException {

		User user = retrieveUserEntityByUserName(userName);
		ModelMapper modelMapper = new ModelMapper();
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
	    userKafkaProducer.sendMessage(user);
	    return user;
		
	}

	@Override
	public void addWatchedMovie(String userName, MovieDTO watchedMovie) throws UserNotFoundException {
		User user = retrieveUserEntityByUserName(userName);
	    ModelMapper modelMapper = new ModelMapper();
	    Movie movie = modelMapper.map(watchedMovie,Movie.class);
	    user.getWatchedMovies().add(movie);
	    userRepo.save(user);
	}
	
	@Override
	public void addFavMovie(String userName, MovieDTO favMovie) throws UserNotFoundException {
		User user = retrieveUserEntityByUserName(userName);
	    ModelMapper modelMapper = new ModelMapper();
	    Movie movie = modelMapper.map(favMovie,Movie.class);
	    user.getFavMovies().add(movie);
	    userRepo.save(user);
	}
	
	private User retrieveUserEntityByUserName(String userName) throws UserNotFoundException {
		boolean isUserNameExists = userRepo.existsByUserName(userName);
	    if (!isUserNameExists) {
	        throw new UserNotFoundException(userName);
	    }
		return userRepo.findByUserName(userName);
	}	

}
