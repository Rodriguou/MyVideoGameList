package com.rodrigomatos.myvideogamelist.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "game_list")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private GameStatus status = GameStatus.PENDING;
}
