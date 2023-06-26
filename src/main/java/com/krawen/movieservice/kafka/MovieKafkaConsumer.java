package com.krawen.movieservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.krawen.movieservice.config.KafkaTopicConfig;

@Service
public class MovieKafkaConsumer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MovieKafkaConsumer.class);

	@KafkaListener(topics = KafkaTopicConfig.MOVIE_SERVICE_TOPIC_NAME, groupId= "movieServiceGroup")
	public void consume(String message) {
		LOGGER.info(String.format("Message received : %s",message));
	}
}
