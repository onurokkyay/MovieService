package com.krawen.movieservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.krawen.movieservice.config.KafkaTopicConfig;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MovieKafkaProducer {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(MovieKafkaProducer.class);
	
	private KafkaTemplate<String, String> kafkaTemplate;

	public void sendMessage(String message) {
		LOGGER.info(String.format("Kafka message sent %s", message));
		kafkaTemplate.send(KafkaTopicConfig.MOVIE_SERVICE_TOPIC_NAME, message);
	}
}
