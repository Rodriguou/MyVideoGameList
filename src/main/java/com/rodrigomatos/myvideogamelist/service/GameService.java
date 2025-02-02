package com.rodrigomatos.myvideogamelist.service;

import com.rodrigomatos.myvideogamelist.dto.GameDTO;
import com.rodrigomatos.myvideogamelist.entity.Game;
import com.rodrigomatos.myvideogamelist.mapper.GameMapper;
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
    private final GameMapper gameMapper;

    public GameDTO addGame(GameDTO gameDTO) {
        Game newGame = gameMapper.toEntity(gameDTO);
        return gameMapper.toDTO(gameRepository.save(newGame));
    }

    public List<GameDTO> getAllGamesSortedBy(String sort) {
        return gameMapper.toDTOList(gameRepository.findAll(getSortByField(sort)));
    }

    public List<GameDTO> getGamesByName(String name) {
        return gameMapper.toDTOList(gameRepository.findByNameContainingIgnoreCase(name, getSortByField("name")));
    }

    public List<GameDTO> getGamesByReleaseYear(int year) {
        return gameMapper.toDTOList(gameRepository.findByReleaseYear(year, getSortByField("release-date")));
    }

    public GameDTO getGameById(Long id) {
        Game existingGame = gameRepository.findById(id).orElseThrow();
        return gameMapper.toDTO(existingGame);
    }

    public GameDTO updateGame(Long id, GameDTO gameDTO) {
        Game existingGame = gameRepository.findById(id).orElseThrow();
        gameMapper.updateFromDTO(gameDTO, existingGame);
        Game updatedGame = gameRepository.save(existingGame);
        return gameMapper.toDTO(updatedGame);
    }

    @Transactional
    public void deleteGame(Long id) {
        gameListRepository.deleteByGameId(id);
        Game existingGame = gameRepository.findById(id).orElseThrow();
        gameRepository.delete(existingGame);
    }

    private Sort getSortByField(String sort) {
        return switch (sort.toLowerCase()) {
            case "name" -> Sort.by(
                    Sort.Order.asc("name"),
                    Sort.Order.asc("releaseDate")
            );
            case "release-date" -> Sort.by(
                    Sort.Order.asc("releaseDate"),
                    Sort.Order.asc("name")
            );
            default -> Sort.by(Sort.Order.asc("id"));
        };
    }

}
