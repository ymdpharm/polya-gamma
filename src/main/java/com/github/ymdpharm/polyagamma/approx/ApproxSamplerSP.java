package com.github.ymdpharm.polyagamma.approx;

import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.random.RandomGenerator;

public class ApproxSamplerSP implements ApproxSampler {
    private final double b;
    private final double c;
    private final RandomGenerator rng;

    /**
     * pick one from PG(b,c) using saddle point approximation.
     * b must be >= 1.
     */
    public ApproxSamplerSP(double b, double c, RandomGenerator rng) {
        if (b < 1) {
            throw new NumberIsTooSmallException(LocalizedFormats.SHAPE, b, 1, false);
        }
        this.b = b;
        this.c = c;
        this.rng = rng;
    }

    public double sample() {
        // todo: impl
        return 0;
    }
}
