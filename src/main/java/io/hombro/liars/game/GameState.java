package io.hombro.liars.game;

import java.util.ArrayList;
import java.util.List;

import static io.hombro.liars.game.GameState.Event.LOSE;
import static io.hombro.liars.game.GameState.Event.WIN;

public class GameState {
    private int numberOfPlayers;
    private int dicePerPlayer;
    private final List<Event> events;

    public GameState(int numberOfPlayers, int dicePerPlayer) {
        this.numberOfPlayers = numberOfPlayers;
        this.dicePerPlayer = dicePerPlayer;

        events = new ArrayList<>();
    }

    enum Event {
        WIN, LOSE;
    }

    public void playerWins() {
        events.add(WIN);
    }

    public void playerLoses() {
        events.add(LOSE);
    }

    public boolean playerLost() {
        return playerDiceTotal() <= 0;
    }

    public int totalDice() {
        return (dicePerPlayer * numberOfPlayers) - events.size();
    }

    public int playerDiceTotal() {
        return (int) (dicePerPlayer - events.stream().filter(e -> e == LOSE).count());
    }

    public int numberOfOpponentDice() {
        return totalDice() - playerDiceTotal();
    }

    public boolean playerWon() {
        return totalDice() == playerDiceTotal();
    }

    public boolean gameFinished() {
        return playerLost() || playerWon();
    }

    public void undo() {
        events.remove(events.size() - 1);
    }
}
