package com.krawen.movieservice.external.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Person {
    private boolean adult;
    private long id;
    private String name;
    @JsonProperty("original_name")
    private String originalName;
    @JsonProperty("media_type")
    private String mediaType;
    private double popularity;
    private long gender;
    @JsonProperty("known_for_department")
    private String knownForDepartment;
    @JsonProperty("profile_path")
    private String profilePath;
    //private List<KnownFor> knownFor;
}
