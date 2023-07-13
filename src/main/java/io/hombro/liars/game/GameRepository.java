package io.hombro.liars.game;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Consumer;

@Component
public class GameRepository {

    private Optional<GameState> gameState = Optional.empty();

    public void setGameState(GameState gameState) {
        this.gameState = Optional.of(gameState);
    }

    public Optional<GameState> getGame() {
        return gameState;
    }

    public void visit(Consumer<GameState> c) {
        gameState.ifPresent(c);
    }
}
