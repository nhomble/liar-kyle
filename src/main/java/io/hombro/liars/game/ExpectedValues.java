package io.hombro.liars.game;

public class ExpectedValues {
    private final double[] expected;

    public ExpectedValues(double[] expected) {
        this.expected = expected;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < expected.length; i++) {
            sb.append("Expected value of ").append(i + 1).append(" is ").append(expected[i])
                    .append("\n");
        }
        return sb.toString();
    }
}
