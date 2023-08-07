package com.krawen.movieservice.service.impl;

import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.krawen.movieservice.dto.MovieDTO;
import com.krawen.movieservice.dto.MovieDetailDTO;
import com.krawen.movieservice.dto.UserDTO;
import com.krawen.movieservice.entity.Movie;
import com.krawen.movieservice.entity.MovieDetail;
import com.krawen.movieservice.entity.User;
import com.krawen.movieservice.exception.MovieAlreadyExistException;
import com.krawen.movieservice.exception.MovieNotFoundException;
import com.krawen.movieservice.exception.UserNameExistException;
import com.krawen.movieservice.exception.UserNotFoundException;
import com.krawen.movieservice.kafka.UserKafkaProducer;
import com.krawen.movieservice.repository.UserRepository;
import com.krawen.movieservice.service.IMovieService;
import com.krawen.movieservice.service.IUserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	UserKafkaProducer userKafkaProducer;

	@Autowired
	IMovieService movieService;

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
		User user = modelMapper.map(userDTO, User.class);

		if (isUserNameExists) {
			throw new UserNameExistException(user.getUserName());
		}
		user = userRepo.save(user);
		userKafkaProducer.sendMessage(userDTO);
		return user;

	}

	@Override
	public void addWatchedMovie(String userName, int movieId) throws UserNotFoundException, MovieNotFoundException, MovieAlreadyExistException {
		User user = retrieveUserEntityByUserName(userName);
		if (checkIsMovieExistInList(user.getWatchedMovies(), movieId)) {
			throw new MovieAlreadyExistException(movieId);
		} else {
			ModelMapper modelMapper = new ModelMapper();
			MovieDetailDTO watchedMovie = movieService.retrieveMovieById(movieId);
			MovieDetail movie = modelMapper.map(watchedMovie, MovieDetail.class);
			user.getWatchedMovies().add(movie);
			userRepo.save(user);
		}
	}

	@Override
	public void addFavMovie(String userName, int movieId)
			throws UserNotFoundException, MovieNotFoundException, MovieAlreadyExistException {
		User user = retrieveUserEntityByUserName(userName);
		if (checkIsMovieExistInList(user.getFavMovies(), movieId)) {
			throw new MovieAlreadyExistException(movieId);
		} else {
			ModelMapper modelMapper = new ModelMapper();
			MovieDetailDTO favMovie = movieService.retrieveMovieById(movieId);
			MovieDetail movie = modelMapper.map(favMovie, MovieDetail.class);
			user.getFavMovies().add(movie);
			userRepo.save(user);
		}
	}

	@Override
	public void removeWatchedMovie(String userName, int id) throws UserNotFoundException, MovieNotFoundException {
		User user = retrieveUserEntityByUserName(userName);
		user.removeWatchedMovieByName(id);
		userRepo.save(user);
	}

	@Override
	public void removeFavMovie(String userName, int id) throws UserNotFoundException, MovieNotFoundException {
		User user = retrieveUserEntityByUserName(userName);
		user.removeFavMovieByName(id);
		userRepo.save(user);
	}

	private User retrieveUserEntityByUserName(String userName) throws UserNotFoundException {
		boolean isUserNameExists = userRepo.existsByUserName(userName);
		if (!isUserNameExists) {
			throw new UserNotFoundException(userName);
		}
		return userRepo.findByUserName(userName);
	}

	private boolean checkIsMovieExistInList(List<MovieDetail> movieList, int movieId) {
		return !Objects.isNull(movieList.stream().filter(movie -> movie.getId() == movieId).findFirst().orElse(null));
	}

}
