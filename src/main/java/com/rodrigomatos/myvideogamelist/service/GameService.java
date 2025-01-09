package com.rodrigomatos.myvideogamelist.service;

import com.rodrigomatos.myvideogamelist.dto.GameDTO;
import com.rodrigomatos.myvideogamelist.entity.Game;
import com.rodrigomatos.myvideogamelist.repository.GameListRepository;
import com.rodrigomatos.myvideogamelist.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final GameListRepository gameListRepository;

    public GameDTO addGame(GameDTO gameDTO) {
        Game game = new Game(gameDTO.name().trim(), gameDTO.releaseDate());
        game = gameRepository.save(game);
        return new GameDTO(game.getId(), game.getName(), game.getReleaseDate());
    }

    public List<GameDTO> getAllGamesSortedByName() {
        return convertToDTOList(
                gameRepository.findAll(Sort.by(
                        Sort.Order.asc("name"),
                        Sort.Order.asc("releaseDate")
                ))
        );
    }

    public List<GameDTO> getAllGamesSortedByReleaseDate() {
        return convertToDTOList(
                gameRepository.findAll(Sort.by(
                        Sort.Order.asc("releaseDate"),
                        Sort.Order.asc("name")
                ))
        );
    }

    public List<GameDTO> findGamesByName(String name) {
        return convertToDTOList(
                gameRepository.findByNameContainingIgnoreCase(
                        name,
                        Sort.by(Sort.Order.asc("name"), Sort.Order.asc("releaseDate"))
                )
        );
    }

    public List<GameDTO> getGamesByReleaseYear(int year) {
        return convertToDTOList(
                gameRepository.findByReleaseYear(
                        year,
                        Sort.by(Sort.Order.asc("releaseDate"), Sort.Order.asc("name"))
                )
        );
    }

    public GameDTO getGameById(Long id) {
        Game game = gameRepository.findById(id).orElseThrow();
        return new GameDTO(game.getId(), game.getName(), game.getReleaseDate());
    }

    public GameDTO updateGame(Long id, GameDTO gameDTO) {
        Game game = gameRepository.findById(id).orElseThrow();
        game.setName(gameDTO.name().trim());
        game.setReleaseDate(gameDTO.releaseDate());
        game = gameRepository.save(game);
        return new GameDTO(game.getId(), game.getName(), game.getReleaseDate());
    }

    @Transactional
    public void deleteGame(Long id) {
        gameListRepository.deleteByGameId(id);
        Game game = gameRepository.findById(id).orElseThrow();
        gameRepository.delete(game);
    }

    private List<GameDTO> convertToDTOList(List<Game> games) {
        return games.stream()
                .map(game -> new GameDTO(game.getId(), game.getName(), game.getReleaseDate()))
                .toList();
    }

}
