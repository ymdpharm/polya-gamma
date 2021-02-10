package com.github.ymdpharm.approx;

import org.apache.commons.math3.random.RandomGenerator;

/*
Alternative Approximation

see: Bayesian inference for logistic models using Polya-Gamma latent variables
https://arxiv.org/abs/1205.0310
 */
public class ApproxSamplerAlt implements ApproxSampler {
    private final double b;
    private final double c;

    public ApproxSamplerAlt(double b, double c) {
        this.b = b;
        this.c = c;
    }

    public double sample(RandomGenerator rng) {
        return 0;
    }
}
