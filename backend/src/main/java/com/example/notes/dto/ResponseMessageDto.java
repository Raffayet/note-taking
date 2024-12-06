package com.example.notes.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ResponseMessageDto {
    private String message;

    public ResponseMessageDto(String message) {
        this.message = message;
    }
}