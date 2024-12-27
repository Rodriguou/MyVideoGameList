package br.com.rodrigomatos.myvideogamelist.api.repository;

import br.com.rodrigomatos.myvideogamelist.api.entity.Game;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByNameContainingIgnoreCase(String name, Sort sort);

    @Query("SELECT g FROM Game g WHERE YEAR(g.releaseDate) = :year")
    List<Game> findByReleaseYear(@Param("year") Integer year, Sort sort);
}
