package com.github.ymdpharm.approx;

import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.random.RandomGenerator;

public class ApproxSamplerDevroyeInt implements ApproxSampler {
    private final int b;
    private final double c;
    private RandomGenerator rng;

    /**
     * pick one from PG(b,c)
     * b must be a positive integer.
     */
    public ApproxSamplerDevroyeInt(int b, double c, RandomGenerator rng) {
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
