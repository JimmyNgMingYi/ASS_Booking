package com.nusiss.ass.booking.dto;

public class BookingErrorResponseDto implements BookingBaseResponse {
    private String error;
    private String message;

    public BookingErrorResponseDto(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
