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
        Game game = gameRepository.findById(gameListDTO.gameId()).orElseThrow();
        GameList gameList = GameList.builder()
                .game(game)
                .status(GameStatus.PENDING)
                .build();
        gameList = gameListRepository.save(gameList);
        return gameListMapper.toDTO(gameList);
    }

    public List<GameListDTO> getGameListSortedByName() {
        return gameListMapper.toDTOList(
                gameListRepository.findAll(Sort.by(
                        Sort.Order.asc("game.name"),
                        Sort.Order.asc("game.releaseDate")
                ))
        );
    }

    public List<GameListDTO> getGameListSortedByReleaseDate() {
        return gameListMapper.toDTOList(
                gameListRepository.findAll(Sort.by(
                        Sort.Order.asc("game.releaseDate"),
                        Sort.Order.asc("game.name")
                ))
        );
    }

    public List<GameListDTO> findGamesByName(String name) {
        return gameListMapper.toDTOList(
                gameListRepository.findByGameNameContainingIgnoreCase(
                        name,
                        Sort.by(
                                Sort.Order.asc("game.name"),
                                Sort.Order.asc("game.releaseDate")
                        )
                )
        );
    }

    public List<GameListDTO> getGameListByReleaseYear(int year) {
        return gameListMapper.toDTOList(
                gameListRepository.findByGameReleaseYear(
                        year,
                        Sort.by(
                                Sort.Order.asc("game.releaseDate"),
                                Sort.Order.asc("game.name")
                        )
                )
        );
    }

    public List<GameListDTO> getGameListByStatus(GameStatus status) {
        return gameListMapper.toDTOList(
                gameListRepository.findByStatus(
                        status,
                        Sort.by(
                                Sort.Order.asc("game.name"),
                                Sort.Order.asc("game.releaseDate")
                        )
                )
        );
    }

    public GameListDTO getGameById(Long id) {
        GameList gameList = gameListRepository.findById(id).orElseThrow();
        return gameListMapper.toDTO(gameList);
    }

    public GameListDTO updateGameStatus(Long id, GameStatus status) {
        GameList gameList = gameListRepository.findById(id).orElseThrow();
        gameList.setStatus(status);
        gameList = gameListRepository.save(gameList);
        return gameListMapper.toDTO(gameList);
    }

    public void removeGameFromList(Long id) {
        GameList game = gameListRepository.findById(id).orElseThrow();
        gameListRepository.delete(game);
    }

}
