package com.krawen.movieservice.external.service.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SearchPersonResponse {
    private long page;
    @JsonProperty("results")
    private List<Person> personList;
    private long totalPages;
    private long totalResults;
}
