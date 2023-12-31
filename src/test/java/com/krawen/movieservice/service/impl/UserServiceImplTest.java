package com.krawen.movieservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.krawen.movieservice.dto.MovieDetailDTO;
import com.krawen.movieservice.dto.UserDTO;
import com.krawen.movieservice.entity.MovieDetail;
import com.krawen.movieservice.entity.User;
import com.krawen.movieservice.exception.MovieAlreadyExistException;
import com.krawen.movieservice.exception.MovieNotFoundException;
import com.krawen.movieservice.exception.UserNameExistException;
import com.krawen.movieservice.exception.UserNotFoundException;
import com.krawen.movieservice.kafka.UserKafkaProducer;
import com.krawen.movieservice.repository.UserRepository;

class UserServiceImplTest {

	private UserRepository userRepo;
	private UserKafkaProducer userKafkaProducer;
	private UserServiceImpl userService;
	private MovieServiceImpl movieService;
	private MovieDetailDTO movieDetailDto;
	private MovieDetail movieDetail;
	private static final String TEST_USERNAME = "test";
	private int movieId = 1;
	UserDTO userDTO;
	User savedUser;
	User user = new User();

	@BeforeEach
	void setup() {
		userRepo = mock(UserRepository.class);
		userKafkaProducer = mock(UserKafkaProducer.class);
		userDTO = new UserDTO();
		userDTO.setUserName(TEST_USERNAME);
		savedUser = new User();
		savedUser.setUserName(TEST_USERNAME);
		savedUser.setId("1");
		user = new User();
		user.setUserName(TEST_USERNAME);

		movieDetailDto = new MovieDetailDTO();
		movieDetailDto.setId(movieId);

		movieDetail = new MovieDetail();
		movieDetail.setId(movieId);

		movieService = mock(MovieServiceImpl.class);
		userService = new UserServiceImpl(userRepo, userKafkaProducer, movieService);
	}

	@Test
    void testCreateUserSuccess() throws UserNameExistException {

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
    void testCreateUserUserNameExists() throws UserNameExistException {

        when(userRepo.existsByUserName(userDTO.getUserName())).thenReturn(true);

        assertThrows(UserNameExistException.class, () -> userService.createUser(userDTO));
        verify(userRepo, times(1)).existsByUserName(userDTO.getUserName());
        verify(userRepo, times(0)).save(any(User.class));
        verify(userKafkaProducer, times(0)).sendMessage(any(UserDTO.class));
    }

	@Test
    void testRetrieveUserSuccess() throws UserNotFoundException {

        when(userRepo.existsByUserName(TEST_USERNAME)).thenReturn(true);

        when(userRepo.findByUserName(any(String.class))).thenReturn(user);

        UserDTO userDTO = userService.retrieveUserByUserName(TEST_USERNAME);

        assertNotNull(userDTO);
        assertEquals(userDTO.getUserName(), user.getUserName());
        verify(userRepo, times(1)).existsByUserName(userDTO.getUserName());
        verify(userRepo, times(1)).findByUserName(TEST_USERNAME);

    }

	@Test
    void testRetrieveUserUserNotFound() throws UserNotFoundException {

        when(userRepo.existsByUserName(userDTO.getUserName())).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.retrieveUserByUserName(userDTO.getUserName()));
        verify(userRepo, times(1)).existsByUserName(userDTO.getUserName());
        verify(userRepo, times(0)).findByUserName(any(String.class));

    }

	@Test
    void testdAddWatchedMovieSuccessfully() throws UserNotFoundException, MovieNotFoundException, MovieAlreadyExistException {

        when(userRepo.existsByUserName(userDTO.getUserName())).thenReturn(true);
        
        when(userRepo.findByUserName(any(String.class))).thenReturn(user);
        
        when(movieService.retrieveMovieById(movieId)).thenReturn(movieDetailDto);
        
        userService.addWatchedMovie(TEST_USERNAME, movieId);
        
        assertNotNull(user.getWatchedMovies().stream().filter(movie -> movie.getId() == movieId).findFirst().orElse(null));

    }

	@Test
	void testAddWatchedMovieMovieAlreadyExistException()
			throws UserNotFoundException, MovieNotFoundException, MovieAlreadyExistException {

		user.getWatchedMovies().add(movieDetail);
		when(userRepo.existsByUserName(userDTO.getUserName())).thenReturn(true);

		when(userRepo.findByUserName(any(String.class))).thenReturn(user);

		assertThrows(MovieAlreadyExistException.class, () -> userService.addWatchedMovie(TEST_USERNAME, movieId));

	}

	@Test
    void testAddFavMovieSuccessfully() throws UserNotFoundException, MovieNotFoundException, MovieAlreadyExistException {

        when(userRepo.existsByUserName(userDTO.getUserName())).thenReturn(true);
        
        when(userRepo.findByUserName(any(String.class))).thenReturn(user);
        
        when(movieService.retrieveMovieById(movieId)).thenReturn(movieDetailDto);
        
        userService.addFavMovie(TEST_USERNAME, movieId);
        
        assertNotNull(user.getFavMovies().stream().filter(movie -> movie.getId() == movieId).findFirst().orElse(null));

    }

	@Test
	void testAddFavMovieMovieAlreadyExistException()
			throws UserNotFoundException, MovieNotFoundException, MovieAlreadyExistException {

		user.getFavMovies().add(movieDetail);
		when(userRepo.existsByUserName(userDTO.getUserName())).thenReturn(true);

		when(userRepo.findByUserName(any(String.class))).thenReturn(user);

		assertThrows(MovieAlreadyExistException.class, () -> userService.addFavMovie(TEST_USERNAME, movieId));

	}

	@Test
	void testDeleteWatchedMovieSuccessfully()
			throws UserNotFoundException, MovieNotFoundException, MovieAlreadyExistException {

		user.getWatchedMovies().add(movieDetail);

		when(userRepo.existsByUserName(userDTO.getUserName())).thenReturn(true);

		when(userRepo.findByUserName(any(String.class))).thenReturn(user);

		userService.removeWatchedMovie(user.getUserName(), movieId);

		assertNull(user.getWatchedMovies().stream().filter(movie -> movie.getId() == movieId).findFirst().orElse(null));
	}

	@Test
	void testDeleteFavMovieSuccessfully()
			throws UserNotFoundException, MovieNotFoundException, MovieAlreadyExistException {

		user.getFavMovies().add(movieDetail);

		when(userRepo.existsByUserName(userDTO.getUserName())).thenReturn(true);

		when(userRepo.findByUserName(any(String.class))).thenReturn(user);

		userService.removeFavMovie(user.getUserName(), movieId);

		assertNull(user.getFavMovies().stream().filter(movie -> movie.getId() == movieId).findFirst().orElse(null));
	}

}
