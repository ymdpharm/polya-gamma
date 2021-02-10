package com.github.ymdpharm.approx;

import org.apache.commons.math3.random.RandomGenerator;

public class ApproxSamplerSP implements ApproxSampler {
    private final double b;
    private final double c;

    public ApproxSamplerSP(double b, double c) {
        this.b = b;
        this.c = c;
    }

    public double sample(RandomGenerator rng) {
        return 0;
    }
}
