package io.hombro.liars.game;

import java.util.Arrays;

public class Turn {

    private final Integer[] dice;

    public Turn(Integer[] dice) {
        this.dice = dice;
    }

    public int numberOf(int value) {
        return (int) Arrays.stream(dice).filter(v -> v == value).count();
    }
}
