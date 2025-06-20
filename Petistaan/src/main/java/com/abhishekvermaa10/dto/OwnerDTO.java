package com.abhishekvermaa10.dto;

import com.abhishekvermaa10.enums.Gender;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author abhishekvermaa10
 */
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class OwnerDTO {

	@EqualsAndHashCode.Include
	@Schema(description = "Unique ID of the owner")
	private int id;
	@Schema(description = "First name of the owner")
	@NotBlank(message="${owner.first.name.required}")
	@Size(max = 250 ,message = "${owner.first.name.length}")
	private String firstName;
	@Schema(description = "Last name of the owner")
	@NotBlank(message="${owner.last.name.required}")
	@Size(max = 250 ,message = "${owner.last.name.length}")
	private String lastName;
	@Schema(description = "Gender of the owner")
	@NotNull(message="owner.gender.required")
	private Gender gender;
	@NotBlank(message = "${owner.city.required}")
	@Size(max=250,message = "${owner.city.length}")
	@Schema(description = "City of the owner")
	private String city;
	@Schema(description = "State of the owner")
	@NotBlank(message = "${owner.state.required}")
	@Size(max=250,message = "${owner.state.length}")
	private String state;
	@Schema(description = "Mobile number of the owner")
	@NotBlank(message = "${owner.mobile.number.required}")
	@Size(max=250,message = "${owner.mobile.number.length}")
	@EqualsAndHashCode.Include
	private String mobileNumber;
	@EqualsAndHashCode.Include
	@Schema(description = "Email id of the owner")
	@Email(message = "{owner.email.invalid}")
    @NotBlank(message = "{owner.email.required}")
	private String emailId;
	@Valid
	@Schema(description = "Pet info of the owner")
    @NotNull(message = "{owner.pet.required}")
	private PetDTO petDTO;
}
