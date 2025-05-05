package com.airline.DTO;

public class CancelResponseDTO {
    private String message;

    public CancelResponseDTO() {
    }

    public CancelResponseDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

