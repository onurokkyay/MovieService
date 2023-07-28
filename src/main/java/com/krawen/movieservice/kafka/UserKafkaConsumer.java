package com.krawen.movieservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.krawen.movieservice.config.KafkaTopicConfig;
import com.krawen.movieservice.entity.User;

@Service
public class UserKafkaConsumer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserKafkaConsumer.class);

	@KafkaListener(topics = KafkaTopicConfig.MOVIE_SERVICE_USER_TOPIC_NAME, groupId= "userServiceGroup")
	public void consume(User user) {
		LOGGER.info(String.format("Message received : %s",user.toString()));
	}
}
