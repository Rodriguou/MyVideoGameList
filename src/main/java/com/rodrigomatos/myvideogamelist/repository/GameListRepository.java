package com.rodrigomatos.myvideogamelist.repository;

import com.rodrigomatos.myvideogamelist.entity.GameList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameListRepository extends JpaRepository<GameList, Long> {
}
