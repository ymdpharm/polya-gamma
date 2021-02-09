package com.github.ymdpharm.approx;

/*
Devroye Approximation

see: Bayesian inference for logistic models using Polya-Gamma latent variables
https://arxiv.org/abs/1205.0310
 */
public class ApproxSamplerDevroye implements ApproxSampler {
    private final double b;
    private final double c;

    public ApproxSamplerDevroye(double b, double c) {
        this.b = b;
        this.c = c;
    }

    public double sample() {
        return 0;
    }
}
