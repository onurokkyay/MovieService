package com.krawen.movieservice;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.krawen.movieservice.controller.MovieController;
import com.krawen.movieservice.service.IMovieService;

@SpringBootTest
class MovieserviceApplicationTests {
	
    @Autowired
    private MovieController movieController;

    @Autowired
    private IMovieService movieService;

	@Test
	void contextLoads() {
		assertNotNull(movieController);
		assertNotNull(movieService);
	}

}
