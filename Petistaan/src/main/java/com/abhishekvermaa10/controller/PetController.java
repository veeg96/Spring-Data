package com.abhishekvermaa10.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abhishekvermaa10.dto.AverageAgeDTO;
import com.abhishekvermaa10.dto.ErrorDTO;
import com.abhishekvermaa10.dto.PetDTO;
import com.abhishekvermaa10.exception.PetNotFoundException;
import com.abhishekvermaa10.service.PetService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
// import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/pets")
@Validated
@RequiredArgsConstructor
@Tag(name="Controller",description = "APIs for managing pets")
// @CrossOrigin(origins ="")
public class PetController {
    private final PetService petService;

    // @CrossOrigin
    @GetMapping("/{petId}")
    @Operation(summary="get pet by id",description = "get pet by id else throw pet not found")
    @ApiResponse(responseCode = "400",description="Bad Request",content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @ApiResponse(responseCode = "201",description = "Created Successfully")
    @ApiResponse(responseCode = "500", description = "Internal Server Error",content = @Content(schema=@Schema(implementation=ErrorDTO.class)))
    public ResponseEntity<PetDTO> findPet(@PathVariable("petId") @Min(value=1,message="${pet.id.positive}") int petId) throws PetNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(petService.findPet(petId));
    }

    // @CrossOrigin
    @GetMapping
    @Operation(summary="get average age of pet",description = "get average age of pet")
    @ApiResponse(responseCode = "400",description="Bad Request",content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @ApiResponse(responseCode = "201",description = "Created Successfully")
    @ApiResponse(responseCode = "500", description = "Internal Server Error",content = @Content(schema=@Schema(implementation=ErrorDTO.class)))
    public ResponseEntity<AverageAgeDTO> getAverageAge() {
        return ResponseEntity.status(HttpStatus.OK).body(petService.findAverageAgeOfPet());
    }
    
    
}
