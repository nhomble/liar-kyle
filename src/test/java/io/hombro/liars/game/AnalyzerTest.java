package io.hombro.liars.game;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnalyzerTest {

    private Analyzer analyzer = new Analyzer();
    double eps = 0.0001;

    @Nested
    class Exactly {
        @Test
        void justOneAgainstOther() {
            GameState gameState = new GameState(2, 1);
            Turn turn = new Turn(new Integer[]{1});
            double out = analyzer.calculateExactly(gameState, turn, 6, 1);
            double diff = Math.abs(out - (1.0 / 6.0));
            assertTrue(diff < eps);
        }
    }

    @Nested
    class GreaterOrMore {

        @Test
        void currentPlayerCovers() {
            GameState gameState = new GameState(2, 1);
            Turn turn = new Turn(new Integer[]{6});
            double out = analyzer.calculateGreaterOrEqualTo(gameState, turn, 6, 1);
            double diff = Math.abs(out - 1);
            assertTrue(diff < eps);
        }

        @Test
        void currentPlayerCoversMany() {
            GameState gameState = new GameState(2, 5);
            Turn turn = new Turn(new Integer[]{6, 6, 6, 6, 6});
            double out = analyzer.calculateGreaterOrEqualTo(gameState, turn, 6, 1);
            double diff = Math.abs(out - 1);
            assertTrue(diff < eps);
        }

        @Test
        void justOneAgainstOther() {
            GameState gameState = new GameState(2, 1);
            Turn turn = new Turn(new Integer[]{1});
            double out = analyzer.calculateGreaterOrEqualTo(gameState, turn, 6, 1);
            double diff = Math.abs(out - (1.0 / 6.0));
            assertTrue(diff < eps);
        }

        @Test
        void betterThanZero() {
            GameState gameState = new GameState(5, 5);
            Turn turn = new Turn(new Integer[]{1, 2, 3, 4, 5});
            assertTrue(analyzer.calculateGreaterOrEqualTo(gameState, turn, 6, 1) > .1);
        }

        @Test
        void law() {
            double d = 0;
            GameState gameState = new GameState(2, 5);
            Turn turn = new Turn(new Integer[]{1, 1, 1, 1, 1});
            for (int i = 0; i <= 5; i++) {
                d += analyzer.calculateExactly(gameState, turn, 6, i);
            }
            double diff = Math.abs(1 - d);
            assertTrue(diff < eps);
        }

        @Test
        void all() {
            GameState gameState = new GameState(2, 5);
            Turn turn = new Turn(new Integer[]{1, 1, 1, 1, 1});
            double diff = Math.abs(1 - analyzer.calculateGreaterOrEqualTo(gameState, turn, 6, 0));
            assertTrue(diff < eps);
        }
    }
}
