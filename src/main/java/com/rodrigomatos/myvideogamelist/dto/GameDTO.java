package com.rodrigomatos.myvideogamelist.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record GameDTO(@Schema(accessMode = Schema.AccessMode.READ_ONLY) Long id,
                      @NotBlank(message = "Name is required") @Size(max = 100, message = "Name must be at most 100 characters") String name,
                      @NotNull(message = "Release date is required") LocalDate releaseDate) {
}
