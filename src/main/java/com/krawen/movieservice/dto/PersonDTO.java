package com.krawen.movieservice.dto;

import lombok.Data;

@Data
public class PersonDTO {
    private boolean adult;
    private long id;
    private String name;
    private String originalName;
    private String mediaType;
    private double popularity;
    private long gender;
    private String knownForDepartment;
    private String profilePath;
    //private List<KnownFor> knownFor;
}
