package com.rodrigomatos.myvideogamelist.repository;

import com.rodrigomatos.myvideogamelist.entity.GameList;
import com.rodrigomatos.myvideogamelist.entity.GameStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameListRepository extends JpaRepository<GameList, Long> {

    @Modifying
    @Query("DELETE FROM GameList gl WHERE gl.game.id = :gameId")
    void deleteByGameId(Long gameId);

    List<GameList> findByGameNameContainingIgnoreCase(String name, Sort sort);

    List<GameList> findByStatus(GameStatus status);

}
