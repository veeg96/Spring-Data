package com.abhishekvermaa10.util;

import java.util.Objects;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.abhishekvermaa10.dto.OwnerPetInfoDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OwnerPetInfoMapper {
    default OwnerPetInfoDTO convOwnerPetInfoDTO(Object[] objArray){
        String INVALID_ARRAY = "Invalid object array.";

        if(Objects.isNull(objArray) || objArray.length <4)
            throw new IllegalArgumentException(INVALID_ARRAY);
        else
            return OwnerPetInfoDTO.builder().id((Integer) objArray[0])
                                            .firstName((String) objArray[1])
                                            .lastName((String) objArray[2])
                                            .petName((String)objArray[3]).build();
    }

}
