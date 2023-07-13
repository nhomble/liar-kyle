package io.hombro.liars.game;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Consumer;

@Component
public class TurnRepository {

    private Optional<Turn> turn = Optional.empty();

    public void setTurn(Turn turn) {
        this.turn = Optional.of(turn);
    }

    public void clear() {
        turn = Optional.empty();
    }

    public Optional<Turn> getTurn() {
        return turn;
    }
}
