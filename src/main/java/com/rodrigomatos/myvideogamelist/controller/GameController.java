package com.rodrigomatos.myvideogamelist.controller;

import com.rodrigomatos.myvideogamelist.dto.GameDTO;
import com.rodrigomatos.myvideogamelist.service.GameService;
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
    public ResponseEntity<GameDTO> addGame(@Valid @RequestBody GameDTO gameDTO) {
        GameDTO createdGame = gameService.addGame(gameDTO);
        return new ResponseEntity<>(createdGame, HttpStatus.CREATED);
    }

    @GetMapping("/sorted-by-name")
    public ResponseEntity<List<GameDTO>> getAllGamesSortedByName() {
        List<GameDTO> games = gameService.getAllGamesSortedByName();
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @GetMapping("/sorted-by-release-date")
    public ResponseEntity<List<GameDTO>> getAllGamesSortedByReleaseDate() {
        List<GameDTO> games = gameService.getAllGamesSortedByReleaseDate();
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<GameDTO>> searchGamesByName(@RequestParam String name) {
        List<GameDTO> games = gameService.searchGamesByName(name);
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<List<GameDTO>> getGamesByReleaseYear(@PathVariable("year") int year) {
        List<GameDTO> games = gameService.getGamesByReleaseYear(year);
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameDTO> getGameById(@PathVariable("id") Long id) {
        GameDTO game = gameService.getGameById(id);
        return new ResponseEntity<>(game, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameDTO> updateGame(@PathVariable("id") Long id, @Valid @RequestBody GameDTO gameDTO) {
        GameDTO updatedGame = gameService.updateGame(id, gameDTO);
        return new ResponseEntity<>(updatedGame, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable("id") Long id) {
        gameService.deleteGame(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
