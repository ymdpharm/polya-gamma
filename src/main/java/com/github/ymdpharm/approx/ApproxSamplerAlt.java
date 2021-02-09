package com.github.ymdpharm.approx;

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

    public double sample() {
        return 0;
    }
}
