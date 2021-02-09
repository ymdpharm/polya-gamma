package com.github.ymdpharm.approx;

/*
Saddle-point Approximation

see: Improved Polya-Gamma Sampling
http://www2.stat.duke.edu/~jbw44/Papers/NewPGSamplerWriteUp.pdf
 */
public class ApproxSamplerSP implements ApproxSampler {
    private final double b;
    private final double c;

    public ApproxSamplerSP(double b, double c) {
        this.b = b;
        this.c = c;
    }

    public double sample() {
        return 0;
    }
}
