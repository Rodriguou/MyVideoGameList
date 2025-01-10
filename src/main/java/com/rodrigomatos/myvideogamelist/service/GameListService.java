package com.rodrigomatos.myvideogamelist.service;

import com.rodrigomatos.myvideogamelist.dto.GameListDTO;
import com.rodrigomatos.myvideogamelist.entity.Game;
import com.rodrigomatos.myvideogamelist.entity.GameList;
import com.rodrigomatos.myvideogamelist.entity.GameStatus;
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

    public GameListDTO addGameToList(GameListDTO gameListDTO) {
        Game game = gameRepository.findById(gameListDTO.gameId()).orElseThrow();

        GameList gameList = new GameList(game, GameStatus.PENDING);
        gameList = gameListRepository.save(gameList);

        return new GameListDTO(gameList.getId(), game.getId(), game.getName(), game.getReleaseDate(), gameList.getStatus());
    }

    public List<GameListDTO> getGameListSortedByName() {
        return convertToDTOList(
                gameListRepository.findAll(Sort.by(
                        Sort.Order.asc("game.name"),
                        Sort.Order.asc("game.releaseDate")
                ))
        );
    }

    public List<GameListDTO> getGameListSortedByReleaseDate() {
        return convertToDTOList(
                gameListRepository.findAll(Sort.by(
                        Sort.Order.asc("game.releaseDate"),
                        Sort.Order.asc("game.name")
                ))
        );
    }

    public List<GameListDTO> findGamesByName(String name) {
        return convertToDTOList(
                gameListRepository.findByGameNameContainingIgnoreCase(
                        name,
                        Sort.by(Sort.Order.asc("game.name"), Sort.Order.asc("game.releaseDate"))
                )
        );
    }

    public List<GameListDTO> getGameListByStatus(GameStatus status) {
        return convertToDTOList(
                gameListRepository.findByStatus(status)
        );
    }

    public GameListDTO getGameById(Long id) {
        GameList gameList = gameListRepository.findById(id).orElseThrow();
        return new GameListDTO(gameList.getId(), gameList.getGame().getId(), gameList.getGame().getName(), gameList.getGame().getReleaseDate(), gameList.getStatus());
    }

    public GameListDTO updateGameStatus(Long id, GameStatus status) {
        GameList gameList = gameListRepository.findById(id).orElseThrow();
        gameList.setStatus(status);
        gameList = gameListRepository.save(gameList);

        return new GameListDTO(gameList.getId(), gameList.getGame().getId(), gameList.getGame().getName(), gameList.getGame().getReleaseDate(), gameList.getStatus());
    }

    public void removeGameFromList(Long id) {
        GameList game = gameListRepository.findById(id).orElseThrow();
        gameListRepository.delete(game);
    }

    private List<GameListDTO> convertToDTOList(List<GameList> gameList) {
        return gameList.stream()
                .map(game -> new GameListDTO(game.getId(), game.getGame().getId(), game.getGame().getName(), game.getGame().getReleaseDate(), game.getStatus()))
                .toList();
    }

}