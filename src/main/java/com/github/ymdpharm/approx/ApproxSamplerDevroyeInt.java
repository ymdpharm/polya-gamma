package com.github.ymdpharm.approx;

import org.apache.commons.math3.random.RandomGenerator;

public class ApproxSamplerDevroyeInt implements ApproxSampler {
    private final int b;
    private final double c;

    public ApproxSamplerDevroyeInt(int b, double c) {
        this.b = b;
        this.c = c;
    }

    /*
    pick one from X ~ PG(b,c)
    b must be a positive integer.
     */
    public double sample(RandomGenerator rng) {
        return 0;
    }
}
