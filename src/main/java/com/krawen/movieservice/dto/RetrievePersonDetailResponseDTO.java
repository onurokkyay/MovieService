package com.krawen.movieservice.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class RetrievePersonDetailResponseDTO {
	    private boolean adult;
	    private List<String> alsoKnownAs;
	    private String biography;
	    private LocalDate birthday;
	    private Object deathday;
	    private long gender;
	    private String homepage;
	    private long id;
	    private String imdbId;
	    private String knownForDepartment;
	    private String name;
	    private String placeOfBirth;
	    private double popularity;
	    private String profilePath;
}
