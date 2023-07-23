package com.krawen.movieservice.entity;

import java.util.List;

import lombok.Data;

@Data
public class RetrieveGenresResponse {
	List<Genre> genres;
}
