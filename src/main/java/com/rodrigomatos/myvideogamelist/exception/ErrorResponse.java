package com.rodrigomatos.myvideogamelist.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {
    private final int statusCode;
    private final String timestamp = LocalDateTime.now().toString();
    private final String message;
    private final Map<String, String> errors;
}
