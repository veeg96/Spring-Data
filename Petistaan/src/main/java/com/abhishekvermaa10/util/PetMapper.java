package com.abhishekvermaa10.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.abhishekvermaa10.dto.AverageAgeDTO;
import com.abhishekvermaa10.dto.DomesticPetDTO;
import com.abhishekvermaa10.dto.PetDTO;
import com.abhishekvermaa10.dto.WildPetDTO;
import com.abhishekvermaa10.entity.DomesticPet;
import com.abhishekvermaa10.entity.Pet;
import com.abhishekvermaa10.entity.WildPet;

/**
 * @author abhishekvermaa10
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PetMapper {
	
	String UNSUPPORTED_PET_INSTANCE = "Unsupported pet instance: %s";

	default PetDTO petToPetDTO(Pet pet) {
		return switch (pet) {
		case DomesticPet domesticPet -> domesticPetToDomesticPetDTO(domesticPet);
		case WildPet wildPet -> wildPetToWildPetDTO(wildPet);
		default -> throw new IllegalArgumentException(String.format(UNSUPPORTED_PET_INSTANCE, pet.getClass()));
		};
	}

	@Mapping(source = "domesticPet.owner", target = "ownerDTO")
	@Mapping(target = "ownerDTO.petDTO", ignore = true)
	DomesticPetDTO domesticPetToDomesticPetDTO(DomesticPet domesticPet);

	@Mapping(source = "wildPet.owner", target = "ownerDTO")
	@Mapping(target = "ownerDTO.petDTO", ignore = true)
	WildPetDTO wildPetToWildPetDTO(WildPet wildPet);

	default AverageAgeDTO getAverageAgeDTO(Double average){
		return AverageAgeDTO.builder().averageAge(average).build();
	}
	
}
