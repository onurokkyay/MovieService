package com.krawen.movieservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.krawen.movieservice.entity.MovieDetail;

@Repository
public interface MovieRepository  extends MongoRepository<MovieDetail, String>{
	
    //@Query("{looked : 'false'}")
    // SQL Equivalent : SELECT * FROM chasedUsers where looked = false
    //List<User> retrieveChasedNotLookedUsers();
}
