package com.abhishekvermaa10.dto;

import com.abhishekvermaa10.enums.Gender;
import com.abhishekvermaa10.enums.PetType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author abhishekvermaa10
 */
@NoArgsConstructor
@ToString(callSuper = true)
@Setter
@Getter
public class WildPetDTO extends PetDTO {
	@Schema(description = "place of birth of pet")
	private String birthPlace;

	@Builder
	public WildPetDTO(int id, String name, Gender gender, PetType type, OwnerDTO ownerDTO, String birthPlace) {
		super(id, name, gender, type, ownerDTO);
		this.birthPlace = birthPlace;
	}

}
