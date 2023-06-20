package com.krawen.movieservice.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties("movieservice")
@Data
public class MovieServiceProperties {
	private String movieApiBearerToken;
}
