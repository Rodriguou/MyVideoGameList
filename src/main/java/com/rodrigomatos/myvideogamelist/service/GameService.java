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
        Game game = gameMapper.toEntity(gameDTO);
        game = gameRepository.save(game);
        return gameMapper.toDTO(game);
    }

    public List<GameDTO> getGamesSortedBy(String sort) {
        Sort sorting = switch (sort.toLowerCase()) {
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
        return gameMapper.toDTOList(gameRepository.findAll(sorting));
    }

    public List<GameDTO> findGamesByName(String name) {
        return gameMapper.toDTOList(
                gameRepository.findByNameContainingIgnoreCase(
                        name,
                        Sort.by(
                                Sort.Order.asc("name"),
                                Sort.Order.asc("releaseDate")
                        )
                )
        );
    }

    public List<GameDTO> getGamesByReleaseYear(int year) {
        return gameMapper.toDTOList(
                gameRepository.findByReleaseYear(
                        year,
                        Sort.by(
                                Sort.Order.asc("releaseDate"),
                                Sort.Order.asc("name")
                        )
                )
        );
    }

    public GameDTO getGameById(Long id) {
        Game game = gameRepository.findById(id).orElseThrow();
        return gameMapper.toDTO(game);
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
        Game game = gameRepository.findById(id).orElseThrow();
        gameRepository.delete(game);
    }

}
