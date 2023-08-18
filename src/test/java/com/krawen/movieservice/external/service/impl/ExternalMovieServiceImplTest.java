package com.krawen.movieservice.external.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.krawen.movieservice.dto.SearchPersonResponseDTO;
import com.krawen.movieservice.external.service.dto.SearchPersonResponse;
import com.krawen.movieservice.properties.MovieServiceProperties;

@ExtendWith(MockitoExtension.class)
class ExternalMovieServiceImplTest {

	@InjectMocks
	ExternalMovieServiceImpl extMovieService;

	@Mock
	RestTemplate restTemplate;

	@Mock
	MovieServiceProperties movieServiceProperties;

	ResponseEntity<SearchPersonResponse> searchPersonResponseEntity;
	SearchPersonResponse searchPersonResponse;

	String timeWindow = "day";

	@BeforeEach
	void setup() {
		searchPersonResponse = new SearchPersonResponse();
		searchPersonResponse.setPage(1);
		searchPersonResponseEntity = new ResponseEntity<SearchPersonResponse>(searchPersonResponse, HttpStatus.OK);
		when(movieServiceProperties.getMovieApiBearerToken()).thenReturn("");
	}

	@Test
    void testRetrieveTrendingPeopleSuccess() {
    	
        when(restTemplate.exchange(
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<SearchPersonResponse>>any()))
             .thenReturn(searchPersonResponseEntity);

        SearchPersonResponseDTO searchPersonDto = extMovieService.retrieveTrendingPeople(timeWindow);
        assertEquals(searchPersonDto.getPage(), searchPersonResponseEntity.getBody().getPage());
    }

}
