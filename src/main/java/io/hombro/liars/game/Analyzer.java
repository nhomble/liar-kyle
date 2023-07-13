package io.hombro.liars.game;

import org.springframework.stereotype.Component;

@Component
public class Analyzer {

    public double calculateExactly(GameState gameState, Turn turn, int value, int exactly) {
        double p = 1.0 / 6.0;
        int opponentCount = gameState.numberOfOpponentDice();
        int need = exactly - turn.numberOf(value);
        if (need < 0) {
            return 0;
        }

        return Statistics.binomialDistribution(p, opponentCount, need);
    }

    public double calculateLessThan(GameState gameState, Turn turn, int value, int occurrence) {
        double ret = 0;
        for (int i = 0; i < occurrence; i++) {
            ret += calculateExactly(gameState, turn, value, i);
        }
        return ret;
    }

    public double calculateGreaterOrEqualTo(GameState gameState, Turn turn, int value, int occurrence) {
        return 1 - calculateLessThan(gameState, turn, value, occurrence);
    }
}
