package br.com.rodrigomatos.myvideogamelist.api.controller;

import br.com.rodrigomatos.myvideogamelist.api.entity.Game;
import br.com.rodrigomatos.myvideogamelist.api.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    @PostMapping
    public ResponseEntity<Game> addGame(@RequestBody Game game) {
        Game createdGame = gameService.addGame(game);
        return new ResponseEntity<>(createdGame, HttpStatus.CREATED);
    }

    @GetMapping("/name")
    public ResponseEntity<List<Game>> getAllGamesSortedByName() {
        List<Game> games = gameService.getAllGamesSortedByName();
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @GetMapping("/release-date")
    public ResponseEntity<List<Game>> getAllGamesSortedByReleaseDate() {
        List<Game> games = gameService.getAllGamesSortedByReleaseDate();
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @GetMapping("/search/by-name")
    public ResponseEntity<List<Game>> getGamesByName(@RequestParam String name) {
        List<Game> games = gameService.getGamesByName(name);
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @GetMapping("/search/by-release-year")
    public ResponseEntity<List<Game>> getGamesByReleaseYear(@RequestParam Integer year) {
        List<Game> games = gameService.getGamesByReleaseYear(year);
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Game> editGame(@PathVariable("id") Long id, @RequestBody Game updatedGame) {
        Game editedGame = gameService.editGame(id, updatedGame);
        return new ResponseEntity<>(editedGame, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable("id") Long id) {
        gameService.deleteGame(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
