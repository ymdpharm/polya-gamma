package com.github.ymdpharm.polyagamma.approx;

import com.github.ymdpharm.polyagamma.exception.MaxIterationException;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.random.RandomGenerator;

public class ApproxSamplerWrapper implements ApproxSampler {
    private final double b;
    private final double c;
    private final RandomGenerator rng;
    private final ApproxSampler sampler;

    /**
     * select the most effective sampling algorithm automatically, based on b.
     */
    public ApproxSamplerWrapper(double b, double c, RandomGenerator rng) {
        this.b = b;
        this.c = c;
        this.rng = rng;
        this.sampler = initSampler(b, c, rng);
    }

    private ApproxSampler initSampler(double b, double c, RandomGenerator rng) {
        if (b > 170) {
            return new ApproxSamplerGaussian(b, c, rng);
        } else if (b > 13) {
            return new ApproxSamplerSP(b, c, rng);
        } else if (b > 0) {
            return new ApproxSamplerSumGamma(b, c, rng, 100);
        } else {
            // unreachable
            throw new NotStrictlyPositiveException(LocalizedFormats.SHAPE, b);
        }
    }

    public double sample() {
        return sampler.sample();
    }
}
