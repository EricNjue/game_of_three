package com.example.game_of_three.repository;

import com.example.game_of_three.models.GameMove;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameMoveRepository extends JpaRepository<GameMove, Long> {
}
