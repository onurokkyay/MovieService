package com.krawen.movieservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
	
	public static final String MOVIE_SERVICE_TOPIC_NAME = "movieService";
	
	@Bean
	public NewTopic movieServiceTopic() {
		return TopicBuilder.name(MOVIE_SERVICE_TOPIC_NAME)
				.build();
	}
}
