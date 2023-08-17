package com.krawen.movieservice.dto;

import java.util.List;

import lombok.Data;

@Data
public class SearchPersonResponseDTO {
    private long page;
    private List<PersonDTO> personList;
    private long totalPages;
    private long totalResults;
}
