package com.github.ymdpharm.approx;

import org.apache.commons.math3.random.RandomGenerator;

public class ApproxSamplerWrapper implements ApproxSampler {
    private final double b;
    private final double c;
    private final ApproxSampler alt;
    private final ApproxSampler dev;
    private final ApproxSampler sp;

    public ApproxSamplerWrapper(double b, double c) {
        this.b = b;
        this.c = c;
        this.alt = new ApproxSamplerAlt(b, c);
        this.dev = new ApproxSamplerDevroye(b, c);
        this.sp = new ApproxSamplerSP(b, c);
    }

    public double sample(RandomGenerator rng) {
        if (b > 13) {
            return sp.sample(rng);
        } else if (b == 1 || b == 2) {
            return dev.sample(rng);
        } else if (b > 0) {
            return dev.sample(rng);
        } else {
            return 0;
        }
    }
}
