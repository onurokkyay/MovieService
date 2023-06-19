package com.krawen.movieservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.krawen.movieservice.entity.User;

@Repository
public interface UserRepository  extends MongoRepository<User, String>{
	
    //@Query("{looked : 'false'}")
    // SQL Equivalent : SELECT * FROM chasedUsers where looked = false
    //List<User> retrieveChasedNotLookedUsers();
	
	User findByUserName(String userName);
}
