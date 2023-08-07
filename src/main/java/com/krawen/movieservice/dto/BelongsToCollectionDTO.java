package com.krawen.movieservice.dto;

import lombok.Data;

@Data
public class BelongsToCollectionDTO {
    private Long id;
    private String name;
    private String posterPath;
    private String backdropPath;
}
