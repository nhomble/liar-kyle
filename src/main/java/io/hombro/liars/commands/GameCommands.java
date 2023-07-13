package io.hombro.liars.commands;

import io.hombro.liars.game.GameRepository;
import io.hombro.liars.game.GameState;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@AllArgsConstructor
public class GameCommands {

    private final GameRepository gameRepository;

    @ShellMethod
    public void newGame(@ShellOption("numberOfPlayers") int numberOfPlayers, @ShellOption("dicePerPlayer") int dicePerPlayer) {
        GameState gameState = new GameState(numberOfPlayers, dicePerPlayer);
        gameRepository.setGameState(gameState);
    }
}
