package com.sanotes.web.exception;

import com.sanotes.web.payload.ApiResponse;

public class UnauthorizedException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final ApiResponse apiResponse;
    private final String message;

    public UnauthorizedException(ApiResponse apiResponse) {
        super();
        this.message = apiResponse.getMessage();
        this.apiResponse = apiResponse;
    }


    public UnauthorizedException(String message) {
        super();
        this.message = message;
        apiResponse = new ApiResponse(Boolean.FALSE, message);
    }

    public ApiResponse getApiResponse() {
        return apiResponse;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
