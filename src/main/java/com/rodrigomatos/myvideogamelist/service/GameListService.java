package com.rodrigomatos.myvideogamelist.service;

import com.rodrigomatos.myvideogamelist.dto.GameListDTO;
import com.rodrigomatos.myvideogamelist.entity.Game;
import com.rodrigomatos.myvideogamelist.entity.GameList;
import com.rodrigomatos.myvideogamelist.entity.GameStatus;
import com.rodrigomatos.myvideogamelist.repository.GameListRepository;
import com.rodrigomatos.myvideogamelist.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameListService {
    private final GameListRepository gameListRepository;
    private final GameRepository gameRepository;

    public GameListDTO addGameToList(GameListDTO gameListDTO) {
        Game game = gameRepository.findById(gameListDTO.gameId())
                .orElseThrow();

        GameList gameList = new GameList(game, GameStatus.PENDING);
        gameList = gameListRepository.save(gameList);

        return new GameListDTO(gameList.getId(), game.getId(), game.getName(), game.getReleaseDate(), gameList.getStatus());
    }
}