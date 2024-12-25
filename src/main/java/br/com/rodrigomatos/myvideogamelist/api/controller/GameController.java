package br.com.rodrigomatos.myvideogamelist.api.controller;

import br.com.rodrigomatos.myvideogamelist.api.entity.Game;
import br.com.rodrigomatos.myvideogamelist.api.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    @PostMapping
    public Game addGame(@RequestBody Game game) {
        return gameService.addGame(game);
    }

    @GetMapping("/name")
    public List<Game> getAllGamesSortedByName() {
        return gameService.getAllGamesSortedByName();
    }

    @GetMapping("/release-date")
    public List<Game> getAllGamesSortedByReleaseDate() {
        return gameService.getAllGamesSortedByReleaseDate();
    }

    @GetMapping("/name/{name}")
    public List<Game> getGamesByName(String name) {
        return gameService.getGamesByName(name);
    }

    @GetMapping("/release-year/{year}")
    public List<Game> getGamesByReleaseYear(int year) {
        return gameService.getGamesByReleaseYear(year);
    }

    @PutMapping
    public Game editGame(Long id, Game updatedGame) {
        return gameService.editGame(id, updatedGame);
    }

    @DeleteMapping("{id}")
    public void deleteGame(@PathVariable("id") Long id) {
        gameService.deleteGame(id);
    }
}
