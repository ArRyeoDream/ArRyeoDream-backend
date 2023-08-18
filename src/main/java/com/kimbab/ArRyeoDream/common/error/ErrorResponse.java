package com.kimbab.ArRyeoDream.common.error;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
@Builder
public class ErrorResponse {
    private int status;
    private String message;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode e){
        return ResponseEntity
                .status(e.getStatus())
                .body(ErrorResponse.builder()
                        .status(e.getStatus().value())
                        .message(e.getMessage())
                        .build()
                );
    }

    public static ErrorResponse of(ErrorCode e){
        return new ErrorResponse(e.getStatus().value(), e.getMessage());
    }
}