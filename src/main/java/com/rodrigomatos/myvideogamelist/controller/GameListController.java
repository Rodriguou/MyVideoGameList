package com.rodrigomatos.myvideogamelist.controller;

import com.rodrigomatos.myvideogamelist.dto.GameListDTO;
import com.rodrigomatos.myvideogamelist.service.GameListService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game-list")
@RequiredArgsConstructor
public class GameListController {
    private final GameListService gameListService;

    @PostMapping
    public ResponseEntity<GameListDTO> addGameToList(@Valid @RequestBody GameListDTO gameListDTO) {
        GameListDTO createdGameList = gameListService.addGameToList(gameListDTO);
        return new ResponseEntity<>(createdGameList, HttpStatus.CREATED);
    }
}
