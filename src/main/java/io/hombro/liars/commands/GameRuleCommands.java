package io.hombro.liars.commands;

import io.hombro.liars.game.GameRepository;
import io.hombro.liars.game.GameState;
import io.hombro.liars.game.Turn;
import io.hombro.liars.game.TurnRepository;
import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

import java.util.Arrays;
import java.util.List;

@ShellComponent
@AllArgsConstructor
public class GameRuleCommands {

    private final GameRepository gameRepository;
    private final GameAvailabilityMapper gameAvailabilityMapper;
    private final TurnRepository turnRepository;

    @ShellMethodAvailability
    public Availability availability() {
        return gameAvailabilityMapper.isAvailableForGameRepository(gameRepository);
    }

    @ShellMethod
    public void playerWon() {
        gameRepository.visit(GameState::playerWins);
        turnRepository.clear();
    }

    @ShellMethod
    public void playerLost() {
        gameRepository.visit(GameState::playerLoses);
        turnRepository.clear();
    }

    @ShellMethod
    public void undoLastTurn() {
        gameRepository.visit(GameState::undo);
        turnRepository.clear();
    }

    @ShellMethod
    public String diceLeft() {
        return gameRepository.getGame().map(g -> "Total dice left: " + g.totalDice()).orElse(null);
    }

    @ShellMethod
    public String playerTurn(@ShellOption(value = "currentRoll", help = "comma separated") String commaSeparated) {
        List<Integer> current = Arrays.stream(commaSeparated.split(","))
                .map(String::strip)
                .map(Integer::parseInt)
                .toList();
        if (current.size() != gameRepository.getGame().get().playerDiceTotal()) {
            return "Incorrect number of dice provided. You gave " + current.size() + " but we expected " + gameRepository.getGame().get().playerDiceTotal();
        }
        turnRepository.setTurn(new Turn(current.toArray(new Integer[0])));
        return null;
    }
}
