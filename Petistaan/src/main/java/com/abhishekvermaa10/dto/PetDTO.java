package com.abhishekvermaa10.dto;

import com.abhishekvermaa10.enums.Gender;
import com.abhishekvermaa10.enums.PetType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.validation.constraints.NotNull;




/**
 * @author abhishekvermaa10
 */
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public abstract class PetDTO {

	@EqualsAndHashCode.Include
	 @Schema(description = "unique id of pet")
	private int id;
	@NotBlank(message="${pet.name.required}")
	@Size(max = 250 ,message = "${pet.name.length}")
	@Schema(description = "name of pet")
	private String name;
	@NotNull(message="${pet.gender.required}")
	@Schema(description = "gender of pet")
	@Valid
	private Gender gender;
	@Valid
	@NotNull(message="${pet.type.required}")
	@Schema(description = "type of pet")
	private PetType type;
	@Schema(description = "owner info of pet")
	private OwnerDTO ownerDTO;

}
