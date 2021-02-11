package com.github.ymdpharm.approx;

import org.apache.commons.math3.random.RandomGenerator;

public class ApproxSamplerSP implements ApproxSampler {
    private final double b;
    private final double c;
    private final RandomGenerator rng;

    public ApproxSamplerSP(double b, double c, RandomGenerator rng) {
        this.b = b;
        this.c = c;
        this.rng = rng;
    }

    public double sample() {
        return 0;
    }
}
