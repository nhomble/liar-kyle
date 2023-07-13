package io.hombro.liars.commands;

import io.hombro.liars.game.*;
import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ShellComponent
@AllArgsConstructor
public class AnalysisCommands {

    private final GameRepository gameRepository;
    private final GameAvailabilityMapper gameAvailabilityMapper;
    private final TurnRepository turnRepository;
    private final Analyzer analyzer;

    @ShellMethodAvailability
    public Availability availability() {
        Availability game = gameAvailabilityMapper.isAvailableForGameRepository(gameRepository);
        if (game.isAvailable()) {
            return turnRepository.getTurn().isPresent() ?
                    Availability.available()
                    : Availability.unavailable("your current roll is missing");
        }
        return game;
    }

    @ShellMethod
    public String askForExactly(int value, int occurrence) {
        double d = analyzer.calculateExactly(gameRepository.getGame().get(), turnRepository.getTurn().get(), value, occurrence);
        return String.format("Probability of %d rolling %d time%s is %.2f", value, occurrence, occurrence > 1 ? "s" : "", d);
    }

    @ShellMethod
    public String askEqualOrMore(int value, int occurrence) {
        double d = analyzer.calculateGreaterOrEqualTo(gameRepository.getGame().get(), turnRepository.getTurn().get(), value, occurrence);
        return String.format("Probability of there existing >= %d dice of %d is %.2f", occurrence, value, d);
    }

    record Action(double probability, String recommendation) implements Comparable<Action> {

        @Override
        public int compareTo(Action o) {
            return o.probability < probability ? -1 : 1;
        }

        @Override
        public String toString() {
            return "p=" + probability + " [" + recommendation + "]";
        }
    }

    @ShellMethod
    public String askRecommendation(int value, int occurrence) {
        List<Action> actions = new ArrayList<>();
        GameState gameState = gameRepository.getGame().get();
        Turn turn = turnRepository.getTurn().get();
        actions.add(new Action(analyzer.calculateExactly(gameState, turn, value, occurrence), "Call exact"));
        actions.add(new Action(1 - analyzer.calculateGreaterOrEqualTo(gameState, turn, value, occurrence + 1), "Call liar"));
        for (int i = value; i <= 6; i++) {
            actions.add(new Action(analyzer.calculateGreaterOrEqualTo(gameState, turn, i, occurrence + 1), String.format("%d instances of %d", occurrence + 1, i)));
        }
        Collections.sort(actions);
        return String.format("[%s] with probability %.2f", actions.get(0).recommendation, actions.get(0).probability);
    }
}
