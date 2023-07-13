package io.hombro.liars.game;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StatisticsTest {

    @CsvSource(value = {
            "0,1",
            "1,1",
            "5,120"
    })
    @ParameterizedTest
    void factorial(int i, int o) {
        assertEquals(o, Statistics.factorial(i).longValue());
    }

    @CsvSource(value = {
            "6,1,6",
            "6,6,1",
            "6,2,15"
    })
    @ParameterizedTest
    void choose(int n, int k, int o) {
        assertEquals(o, Statistics.choose(n, k));
    }

    @CsvSource(value = {
            "1,1,1,1"
    })
    @ParameterizedTest
    void binomial(double p, int n, int k, double o) {
        double eps = 0.0001;
        double diff = Math.abs(Statistics.binomialDistribution(p, n, k) - o);
        assertTrue(diff < eps);
    }
}
