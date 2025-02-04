package com.rodrigomatos.myvideogamelist.service;

import com.rodrigomatos.myvideogamelist.dto.GameListDTO;
import com.rodrigomatos.myvideogamelist.entity.Game;
import com.rodrigomatos.myvideogamelist.entity.GameList;
import com.rodrigomatos.myvideogamelist.entity.GameStatus;
import com.rodrigomatos.myvideogamelist.mapper.GameListMapper;
import com.rodrigomatos.myvideogamelist.repository.GameListRepository;
import com.rodrigomatos.myvideogamelist.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameListService {
    private final GameListRepository gameListRepository;
    private final GameRepository gameRepository;
    private final GameListMapper gameListMapper;

    public GameListDTO addGameToList(GameListDTO gameListDTO) {
        Game existingGame = gameRepository.findById(gameListDTO.gameId()).orElseThrow();
        GameList game = GameList.builder()
                .game(existingGame)
                .build();
        return gameListMapper.toDTO(gameListRepository.save(game));
    }

    public List<GameListDTO> getGameListSortedBy(String sort) {
        List<GameList> gameList = gameListRepository.findAll(getSortByField(sort));
        return gameListMapper.toDTOList(gameList);
    }

    public List<GameListDTO> getGamesByName(String name) {
        List<GameList> gameList = gameListRepository.findByGameNameContainingIgnoreCase(name, getSortByField("name"));
        return gameListMapper.toDTOList(gameList);
    }

    public List<GameListDTO> getGamesByReleaseYear(int year) {
        List<GameList> gameList = gameListRepository.findByGameReleaseYear(year, getSortByField("release-date"));
        return gameListMapper.toDTOList(gameList);
    }

    public List<GameListDTO> getGamesByStatus(GameStatus status) {
        List<GameList> gameList = gameListRepository.findByStatus(status, getSortByField("name"));
        return gameListMapper.toDTOList(gameList);
    }

    public GameListDTO getGameById(Long id) {
        GameList listedGame = gameListRepository.findById(id).orElseThrow();
        return gameListMapper.toDTO(listedGame);
    }

    public GameListDTO updateGameStatus(Long id, GameStatus status) {
        GameList listedGame = gameListRepository.findById(id).orElseThrow();
        listedGame.setStatus(status);
        GameList updatedListedGame = gameListRepository.save(listedGame);
        return gameListMapper.toDTO(updatedListedGame);
    }

    public void removeGameFromList(Long id) {
        GameList listedGame = gameListRepository.findById(id).orElseThrow();
        gameListRepository.delete(listedGame);
    }

    private Sort getSortByField(String sort) {
        return switch (sort.toLowerCase()) {
            case "name" -> Sort.by(
                    Sort.Order.asc("gameId"),
                    Sort.Order.asc("gameReleaseDate")
            );
            case "release-date" -> Sort.by(
                    Sort.Order.asc("gameReleaseDate"),
                    Sort.Order.asc("gameName")
            );
            default -> Sort.by(Sort.Order.asc("id"));
        };
    }

}
