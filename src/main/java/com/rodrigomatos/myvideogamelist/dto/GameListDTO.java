package com.rodrigomatos.myvideogamelist.dto;

import com.rodrigomatos.myvideogamelist.entity.GameStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record GameListDTO(@Schema(accessMode = Schema.AccessMode.READ_ONLY) Long id,
                          @NotNull(message = "Game ID is required") Long gameId,
                          @Schema(accessMode = Schema.AccessMode.READ_ONLY) String gameName,
                          @Schema(accessMode = Schema.AccessMode.READ_ONLY) LocalDate gameReleaseDate,
                          @Schema(accessMode = Schema.AccessMode.READ_ONLY) GameStatus status) {
}