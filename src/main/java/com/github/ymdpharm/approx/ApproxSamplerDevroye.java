package com.github.ymdpharm.approx;

import org.apache.commons.math3.distribution.GammaDistribution;
import org.apache.commons.math3.random.RandomGenerator;

public class ApproxSamplerDevroye implements ApproxSampler {
    private final double b;
    private final double c;
    private final int trunc;
    private final GammaDistribution gammaB1;

    public ApproxSamplerDevroye(double b, double c, int trunc) {
        this.b = b;
        this.c = c;
        this.trunc = trunc;
        this.gammaB1 = new GammaDistribution(b, 1);
    }

    /*
    pick one from X ~ PG(b,c)
    with draw_sum_of_gammas approach.
    b must be a positive real number.
     */
    public double sample(RandomGenerator rng) {
        double x = 0;
        for (int k = 0; k < trunc; k++) {
            double gk = gammaB1.sample();
            x += gk / (4 * Math.pow(Math.PI, 2) * Math.pow((k + 0.5), 2) + Math.pow(c, 2));
        }
        return 2 * x;
    }
}
