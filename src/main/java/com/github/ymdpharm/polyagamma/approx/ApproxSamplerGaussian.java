package com.github.ymdpharm.polyagamma.approx;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.random.RandomGenerator;

public class ApproxSamplerGaussian implements ApproxSampler {
    private final double b;
    private final double c;
    private NormalDistribution normM1M2;

    /**
     * pick one from PG(b,c) using simple gaussian approximation.
     * b must be a positive large number.
     */
    public ApproxSamplerGaussian(double b, double c, RandomGenerator rng) {
        if (b <= 0) {
            throw new NotStrictlyPositiveException(LocalizedFormats.SHAPE, b);
        }
        this.b = b;
        this.c = c;
        this.normM1M2 = new NormalDistribution(rng, m1(), Math.sqrt(m2()));
    }

    public double sample() {
        return normM1M2.sample();
    }

    public double m1() {
        return b / 2 / (c + 1e-6) * Math.tanh((c + 1e-6) / 2);
    }

    public double m2() {
        return b / 4 / Math.pow((c + 1e-6), 3) * (Math.sinh((c + 1e-6)) - (c + 1e-6)) / Math.pow(Math.cosh((c + 1e-6) / 2), 2);
    }

}
