package com.github.ymdpharm.approx;

import org.apache.commons.math3.random.RandomGenerator;

public class ApproxSamplerDevroyeInt implements ApproxSampler {
    private final int b;
    private final double c;
    private RandomGenerator rng;

    public ApproxSamplerDevroyeInt(int b, double c, RandomGenerator rng) {
        this.b = b;
        this.c = c;
        this.rng = rng;
    }

    /*
    pick one from X ~ PG(b,c)
    b must be a positive integer.
     */
    public double sample() {
        return 0;
    }
}
