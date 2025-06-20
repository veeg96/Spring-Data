package com.abhishekvermaa10.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorDTO {
    @Schema(description="error message")
    private String message;
    @Schema(description="http status")
    private HttpStatus error;
    @Schema(description="http status code")
    private Integer status;
    @Schema(description="time at the time of exception")
    private LocalDateTime timeStamp;
}
