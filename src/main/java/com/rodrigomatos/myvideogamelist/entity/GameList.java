package com.rodrigomatos.myvideogamelist.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "game_list")
@Getter
@Setter
@NoArgsConstructor
public class GameList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GameStatus status;

    public GameList(Game game, GameStatus status) {
        this.game = game;
        this.status = status;
    }
}
