package com.krawen.movieservice.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SearchPersonResponseDTO {
    private long page;
    private final List<PersonDTO> personList = new ArrayList<PersonDTO>();
    private long totalPages;
    private long totalResults;
}
