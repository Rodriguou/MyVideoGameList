package com.rodrigomatos.myvideogamelist.controller;

import com.rodrigomatos.myvideogamelist.dto.GameListDTO;
import com.rodrigomatos.myvideogamelist.entity.GameStatus;
import com.rodrigomatos.myvideogamelist.service.GameListService;
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
    public ResponseEntity<GameListDTO> addGameToList(@Valid @RequestBody GameListDTO gameListDTO) {
        GameListDTO createdGameList = gameListService.addGameToList(gameListDTO);
        return new ResponseEntity<>(createdGameList, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<GameListDTO>> getGameListSortedBy(@RequestParam(defaultValue = "name") String sort) {
        List<GameListDTO> gameList = gameListService.getGameListSortedBy(sort);
        return new ResponseEntity<>(gameList, HttpStatus.OK);
    }

    @GetMapping("search")
    public ResponseEntity<List<GameListDTO>> findGamesByName(@RequestParam String name) {
        List<GameListDTO> gameList = gameListService.findGamesByName(name);
        return new ResponseEntity<>(gameList, HttpStatus.OK);
    }

    @GetMapping("year/{year}")
    public ResponseEntity<List<GameListDTO>> getGameListByReleaseYear(@PathVariable("year") int year) {
        List<GameListDTO> gameList = gameListService.getGameListByReleaseYear(year);
        return new ResponseEntity<>(gameList, HttpStatus.OK);
    }

    @GetMapping("status/{status}")
    public ResponseEntity<List<GameListDTO>> getGameListByStatus(@PathVariable("status") String status) {
        List<GameListDTO> gameList = gameListService.getGameListByStatus(GameStatus.valueOf(status.toUpperCase()));
        return new ResponseEntity<>(gameList, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<GameListDTO> getGameById(@PathVariable("id") Long id) {
        GameListDTO gameList = gameListService.getGameById(id);
        return new ResponseEntity<>(gameList, HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<GameListDTO> updateGameStatus(@PathVariable("id") Long id, @RequestParam String status) {
        GameListDTO updatedGameList = gameListService.updateGameStatus(id, GameStatus.valueOf(status.toUpperCase()));
        return new ResponseEntity<>(updatedGameList, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeGameFromList(@PathVariable("id") Long id) {
        gameListService.removeGameFromList(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
