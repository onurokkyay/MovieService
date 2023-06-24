package com.krawen.movieservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.krawen.movieservice.config.KafkaTopicConfig;
import com.krawen.movieservice.entity.User;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserKafkaProducer {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(UserKafkaProducer.class);
	
	private KafkaTemplate<String, User> kafkaTemplate;

	public void sendMessage(User user) {
		Message<User> message = MessageBuilder
				.withPayload(user)
				.setHeader(KafkaHeaders.TOPIC, KafkaTopicConfig.MOVIE_SERVICE_USER_TOPIC_NAME)
				.build();
		LOGGER.info(String.format("Kafka message sent %s", message));
		kafkaTemplate.send(message);
	}
}
