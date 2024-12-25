package br.com.rodrigomatos.myvideogamelist.api.service;

import br.com.rodrigomatos.myvideogamelist.api.entity.Game;
import br.com.rodrigomatos.myvideogamelist.api.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;

    public Game addGame(Game game) {
        return gameRepository.save(game);
    }

    public List<Game> getAllGamesSortedByName() {
        Sort sort = Sort.by("name").ascending();
        return gameRepository.findAll(sort);
    }

    public List<Game> getAllGamesSortedByReleaseDate() {
        Sort sort = Sort.by("releaseDate").ascending();
        return gameRepository.findAll(sort);
    }

    public List<Game> getGamesByName(String name) {
        Sort sort = Sort.by("name").ascending();
        return gameRepository.findByNameContainingIgnoreCase(name, sort);
    }

    public List<Game> getGamesByReleaseYear(int year) {
        Sort sort = Sort.by("releaseDate").ascending();
        return gameRepository.findByReleaseYear(year, sort);
    }

    public Game editGame(Long id, Game updatedGame) {
        Game existingGame = gameRepository.findById(id).orElseThrow();
        existingGame.setName(updatedGame.getName());
        existingGame.setReleaseDate(updatedGame.getReleaseDate());
        return gameRepository.save(existingGame);
    }

    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }
}
