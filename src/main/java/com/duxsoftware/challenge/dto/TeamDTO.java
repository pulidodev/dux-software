package com.duxsoftware.challenge.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeamDTO {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String league;

    @NotBlank
    private String country;
}