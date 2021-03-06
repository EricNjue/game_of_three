package com.example.game_of_three.repository;

import com.example.game_of_three.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

  Player findFirstByName(String playerName);
}
