package com.rodrigomatos.myvideogamelist.controller;

import com.rodrigomatos.myvideogamelist.dto.GameDTO;
import com.rodrigomatos.myvideogamelist.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    @PostMapping
    @Operation(summary = "Add a new game")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Game successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request body or validation error")
    })
    public ResponseEntity<GameDTO> addGame(@Valid @RequestBody GameDTO gameDTO) {
        GameDTO createdGame = gameService.addGame(gameDTO);
        return new ResponseEntity<>(createdGame, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all games sorted by a specific field")
    @ApiResponse(responseCode = "200", description = "Games successfully retrieved")
    public ResponseEntity<List<GameDTO>> getGamesSortedBy(@RequestParam String sort) {
        List<GameDTO> games = gameService.getGamesSortedBy(sort);
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @GetMapping("search")
    @Operation(summary = "Find games by name")
    @ApiResponse(responseCode = "200", description = "Games successfully found")
    public ResponseEntity<List<GameDTO>> findGamesByName(@RequestParam String name) {
        List<GameDTO> games = gameService.findGamesByName(name);
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @GetMapping("year/{year}")
    @Operation(summary = "Get games by release year")
    @ApiResponse(responseCode = "200", description = "Games successfully retrieved")
    public ResponseEntity<List<GameDTO>> getGamesByReleaseYear(@PathVariable("year") int year) {
        List<GameDTO> games = gameService.getGamesByReleaseYear(year);
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get a game by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Game successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Game not found")
    })
    public ResponseEntity<GameDTO> getGameById(@PathVariable("id") Long id) {
        GameDTO game = gameService.getGameById(id);
        return new ResponseEntity<>(game, HttpStatus.OK);
    }

    @PutMapping("{id}")
    @Operation(summary = "Update a game by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Game successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request body or validation error"),
            @ApiResponse(responseCode = "404", description = "Game not found")
    })
    public ResponseEntity<GameDTO> updateGame(@PathVariable("id") Long id, @Valid @RequestBody GameDTO gameDTO) {
        GameDTO updatedGame = gameService.updateGame(id, gameDTO);
        return new ResponseEntity<>(updatedGame, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete a game by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Game successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Game not found")
    })
    public ResponseEntity<Void> deleteGame(@PathVariable("id") Long id) {
        gameService.deleteGame(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
