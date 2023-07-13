package io.hombro.liars.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TurnTest {

    private Turn t = new Turn(new Integer[]{1, 2, 2});


    @Test
    void oneOf() {
        assertEquals(1, t.numberOf(1));
    }

    @Test
    void noneOf() {
        assertEquals(0, t.numberOf(0));
    }

    @Test
    void manyOf() {
        assertEquals(2, t.numberOf(2));
    }
}
