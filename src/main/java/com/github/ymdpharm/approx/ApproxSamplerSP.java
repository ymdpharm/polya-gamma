package com.github.ymdpharm.approx;

import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.random.RandomGenerator;

public class ApproxSamplerSP implements ApproxSampler {
    private final double b;
    private final double c;
    private final RandomGenerator rng;

    /**
     * pick one from PG(b,c) using saddle point approximation.
     * b must be a positive real number.
     */
    public ApproxSamplerSP(double b, double c, RandomGenerator rng) {
        if (b <= 0) {
            throw new NotStrictlyPositiveException(LocalizedFormats.SHAPE, b);
        }
        this.b = b;
        this.c = c;
        this.rng = rng;
    }

    public double sample() {
        return 0;
    }
}
