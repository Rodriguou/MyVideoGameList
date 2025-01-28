package com.rodrigomatos.myvideogamelist.controller;

import com.rodrigomatos.myvideogamelist.dto.GameListDTO;
import com.rodrigomatos.myvideogamelist.entity.GameStatus;
import com.rodrigomatos.myvideogamelist.service.GameListService;
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
@RequestMapping("/api/game-list")
@RequiredArgsConstructor
public class GameListController {
    private final GameListService gameListService;


    @PostMapping
    @Operation(summary = "Add a game to the list")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Game successfully added to the list"),
            @ApiResponse(responseCode = "400", description = "Invalid request body or validation error")
    })
    public ResponseEntity<GameListDTO> addGameToList(@Valid @RequestBody GameListDTO gameListDTO) {
        GameListDTO createdGameList = gameListService.addGameToList(gameListDTO);
        return new ResponseEntity<>(createdGameList, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all games in the list sorted by a specific field")
    @ApiResponse(responseCode = "200", description = "Games successfully retrieved")
    public ResponseEntity<List<GameListDTO>> getGameListSortedBy(@RequestParam String sort) {
        List<GameListDTO> gameList = gameListService.getGameListSortedBy(sort);
        return new ResponseEntity<>(gameList, HttpStatus.OK);
    }

    @GetMapping("search")
    @Operation(summary = "Find games in the list by name")
    @ApiResponse(responseCode = "200", description = "Games successfully found")
    public ResponseEntity<List<GameListDTO>> findGamesByName(@RequestParam String name) {
        List<GameListDTO> gameList = gameListService.findGamesByName(name);
        return new ResponseEntity<>(gameList, HttpStatus.OK);
    }

    @GetMapping("year/{year}")
    @Operation(summary = "Get games in the list by release year")
    @ApiResponse(responseCode = "200", description = "Games successfully retrieved")
    public ResponseEntity<List<GameListDTO>> getGameListByReleaseYear(@PathVariable("year") int year) {
        List<GameListDTO> gameList = gameListService.getGameListByReleaseYear(year);
        return new ResponseEntity<>(gameList, HttpStatus.OK);
    }

    @GetMapping("status/{status}")
    @Operation(summary = "Get games in the list by their status")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Games successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Invalid status value")
    })
    public ResponseEntity<List<GameListDTO>> getGameListByStatus(@PathVariable("status") String status) {
        List<GameListDTO> gameList = gameListService.getGameListByStatus(GameStatus.valueOf(status.toUpperCase()));
        return new ResponseEntity<>(gameList, HttpStatus.OK);
    }

    @Operation(summary = "Get a game from the list by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Game successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Game not found")
    })
    @GetMapping("{id}")
    public ResponseEntity<GameListDTO> getGameById(@PathVariable("id") Long id) {
        GameListDTO gameList = gameListService.getGameById(id);
        return new ResponseEntity<>(gameList, HttpStatus.OK);
    }

    @Operation(summary = "Update the status of a game in the list")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Game status successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid status value"),
            @ApiResponse(responseCode = "404", description = "Game not found")
    })
    @PatchMapping("{id}")
    public ResponseEntity<GameListDTO> updateGameStatus(@PathVariable("id") Long id, @RequestParam String status) {
        GameListDTO updatedGameList = gameListService.updateGameStatus(id, GameStatus.valueOf(status.toUpperCase()));
        return new ResponseEntity<>(updatedGameList, HttpStatus.OK);
    }

    @Operation(summary = "Remove a game from the list by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Game successfully removed from the list"),
            @ApiResponse(responseCode = "404", description = "Game not found")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeGameFromList(@PathVariable("id") Long id) {
        gameListService.removeGameFromList(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
