package io.hombro.liars.game;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Statistics {

    public static BigInteger factorial(long n) {
        BigInteger ret = BigInteger.ONE;
        for (int i = 1; i <= n; i++) {
            ret = ret.multiply(BigInteger.valueOf(i));
        }

        return ret;
    }

    public static long choose(long n, long k) {
        BigInteger den = factorial(k).multiply(factorial(n - k));
        return factorial(n).divide(den).longValue();
    }

    public static double binomialDistribution(double p, int n, int k) {
        double q = 1 - p;
        BigDecimal success = new BigDecimal(p).pow(k);
        BigDecimal fail = new BigDecimal(q).pow(n - k);
        BigDecimal choose = BigDecimal.valueOf(choose(n, k));
        return choose.multiply(success.multiply(fail)).doubleValue();
    }
}
