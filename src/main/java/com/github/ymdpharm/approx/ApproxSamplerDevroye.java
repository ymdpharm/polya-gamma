package com.github.ymdpharm.approx;

import org.apache.commons.math3.distribution.GammaDistribution;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.random.RandomGenerator;

public class ApproxSamplerDevroye implements ApproxSampler {
    private final double b;
    private final double c;
    private final int trunc;
    private final GammaDistribution gammaB1;

    /**
     * pick one from PG(b,c) using sum_of_gammas approach.
     * b must be a positive real number.
     */
    public ApproxSamplerDevroye(double b, double c, RandomGenerator rng, int trunc) {
        if (b <= 0) {
            throw new NotStrictlyPositiveException(LocalizedFormats.SHAPE, b);
        }
        this.b = b;
        this.c = c;
        this.trunc = trunc;
        this.gammaB1 = new GammaDistribution(rng, b, 1);
    }

    public double sample() {
        double x = 0;
        for (int k = 0; k < trunc; k++) {
            double gk = gammaB1.sample();
            x += gk / (4 * Math.pow(Math.PI, 2) * Math.pow((k + 0.5), 2) + Math.pow(c, 2));
        }
        return 2 * x;
    }
}
