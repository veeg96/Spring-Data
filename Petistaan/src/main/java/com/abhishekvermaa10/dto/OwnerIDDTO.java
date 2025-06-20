package com.abhishekvermaa10.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
public class OwnerIDDTO {
    @EqualsAndHashCode.Include
    private int id;
}
