package io.hombro.liars.commands;

import io.hombro.liars.game.GameRepository;
import org.springframework.shell.Availability;
import org.springframework.stereotype.Component;

@Component
public class GameAvailabilityMapper {

    public Availability isAvailableForGameRepository(GameRepository gameRepository) {
        if (gameRepository.getGame().isPresent()) {
            return gameRepository.getGame().map(g -> !g.gameFinished()).orElse(false) ?
                    Availability.available()
                    : Availability.unavailable("game is finished");
        } else {
            return Availability.unavailable("no game found");
        }
    }
}
