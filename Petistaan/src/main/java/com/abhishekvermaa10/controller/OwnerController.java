package com.abhishekvermaa10.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abhishekvermaa10.dto.ErrorDTO;
import com.abhishekvermaa10.dto.OwnerDTO;
import com.abhishekvermaa10.dto.OwnerIDDTO;
import com.abhishekvermaa10.dto.OwnerPetInfoDTO;
import com.abhishekvermaa10.dto.UpdatePetDTO;
import com.abhishekvermaa10.exception.OwnerNotFoundException;
import com.abhishekvermaa10.service.OwnerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;





@RestController
@RequestMapping("/owners")
@RequiredArgsConstructor
@Validated
@Tag(name="Controller",description = "APIs for managing owners")
public class OwnerController {
    private final OwnerService ownerService;
    
    @PostMapping
    @Operation(summary = "Create Owner",description = " save owner to database")
    @ApiResponse(responseCode = "400",description = "Bad Request",content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @ApiResponse(responseCode = "500",description = "Internal Server Error",content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @ApiResponse(responseCode = "201",description = "Created Successfully")
    public ResponseEntity<OwnerIDDTO> saveOwner(@Valid @RequestBody OwnerDTO ownerDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ownerService.saveOwner(ownerDTO));
    }

    @GetMapping("/{ownerId}")
    @Operation(summary="get owner by id",description = "get owner by id else throw owner not found")
    @ApiResponse(responseCode = "400",description="Bad Request",content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @ApiResponse(responseCode = "201",description = "Created Successfully")
    @ApiResponse(responseCode = "500", description = "Internal Server Error",content = @Content(schema=@Schema(implementation=ErrorDTO.class)))
    public ResponseEntity<OwnerDTO> findOwnerById(@PathVariable("ownerId") @Min(value=1 ,message="${owner.id.positive}") int ownerId) throws OwnerNotFoundException{
        return ResponseEntity.status(HttpStatus.OK).body(ownerService.findOwner(ownerId));
    }

    @Operation(summary="update owner by id",description = "update owner by id")
    @ApiResponse(responseCode = "400",description="Bad Request",content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @ApiResponse(responseCode = "201",description = "Created Successfully")
    @ApiResponse(responseCode = "500", description = "Internal Server Error",content = @Content(schema=@Schema(implementation=ErrorDTO.class)))
    @PatchMapping("/{ownerId}")
    public ResponseEntity<Void> updatePetDetails(@PathVariable("ownerId") @Min(value=1 ,message="${owner.id.positive}")  int ownerId,@Valid @RequestBody UpdatePetDTO petDTO) throws OwnerNotFoundException{
        ownerService.updatePetDetails(ownerId, petDTO.getPetName());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary="delete owner by id",description = "delete owner by id else throw owner not found")
    @ApiResponse(responseCode = "400",description="Bad Request",content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @ApiResponse(responseCode = "204",description = "no content")
    @ApiResponse(responseCode = "500", description = "Internal Server Error",content = @Content(schema=@Schema(implementation=ErrorDTO.class)))
    @DeleteMapping("/{ownerId}")
    public ResponseEntity<Void> deleteOwner(@PathVariable("ownerId") @Min(value=1 ,message="${owner.id.positive}")  int ownerId) throws OwnerNotFoundException{
        ownerService.deleteOwner(ownerId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    public ResponseEntity<List<OwnerDTO>> findAllOwners() {
        return ResponseEntity.status(HttpStatus.OK).body(ownerService.findAllOwners());
    }
    @Operation(summary="get owner by pages",description = "get owner by pages")
    @ApiResponse(responseCode = "400",description="Bad Request",content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @ApiResponse(responseCode = "201",description = "Created Successfully")
    @ApiResponse(responseCode = "500", description = "Internal Server Error",content = @Content(schema=@Schema(implementation=ErrorDTO.class)))
    @GetMapping("/details")
    public ResponseEntity<Page<OwnerPetInfoDTO>> findIdAndFirstNameAndLastNameAndPetNameOfPaginatedOwners(@Valid Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(ownerService.findIdAndFirstNameAndLastNameAndPetNameOfPaginatedOwners(pageable));
    }
    
    

}
