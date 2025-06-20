package com.abhishekvermaa10.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.Getter;

@Builder
@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OwnerPetInfoDTO {
    @EqualsAndHashCode.Include
    private int id;
    @Schema(description = "first name")
    private String firstName;
    @Schema(description = "last name")
    private String lastName;
    @Schema(description = "pet name")
    private String petName;
}
