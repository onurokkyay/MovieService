package com.krawen.movieservice.external.service.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RetrievePersonDetailResponse {
	    private boolean adult;
	    @JsonProperty("also_known_as")
	    private List<String> alsoKnownAs;
	    private String biography;
	    private LocalDate birthday;
	    private LocalDate deathday;
	    private long gender;
	    private String homepage;
	    private long id;
	    @JsonProperty("imdb_id")
	    private String imdbId;
	    @JsonProperty("known_for_department")
	    private String knownForDepartment;
	    private String name;
	    @JsonProperty("place_of_birth")
	    private String placeOfBirth;
	    private double popularity;
	    @JsonProperty("profile_path")
	    private String profilePath;
}
