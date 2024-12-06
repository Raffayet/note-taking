package com.example.notes.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ResponseMessageDto {
    private String token;

    public ResponseMessageDto(String token) {
        this.token = token;
    }
}