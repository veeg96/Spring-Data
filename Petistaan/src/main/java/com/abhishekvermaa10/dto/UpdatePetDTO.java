package com.abhishekvermaa10.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;


@Getter
public class UpdatePetDTO {
    @Schema(description = "name of pet")
    private String petName;

}
