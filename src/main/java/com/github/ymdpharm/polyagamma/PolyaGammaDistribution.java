package com.github.ymdpharm.polyagamma;

import com.github.ymdpharm.polyagamma.approx.ApproxSampler;
import com.github.ymdpharm.polyagamma.approx.ApproxSamplerWrapper;
import com.github.ymdpharm.polyagamma.approx.ApproxValue;
import com.github.ymdpharm.polyagamma.approx.ApproxValueLaplace;
import org.apache.commons.math3.distribution.AbstractRealDistribution;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;

public class PolyaGammaDistribution extends AbstractRealDistribution {
    private final double b;
    private final double c;
    private final double solverAbsoluteAccuracy;
    private final ApproxSampler approxSampler;
    private final ApproxValue approxValue;

    public PolyaGammaDistribution() throws NotStrictlyPositiveException {
        this(1, 0);
    }

    public PolyaGammaDistribution(double b, double c) throws NotStrictlyPositiveException {
        this(b, c, 1.0E-9D);
    }

    public PolyaGammaDistribution(double b, double c, double inverseCumAccuracy) throws NotStrictlyPositiveException {
        this(new Well19937c(), b, c, inverseCumAccuracy);
    }

    public PolyaGammaDistribution(RandomGenerator rng, double b, double c) throws NotStrictlyPositiveException {
        this(rng, b, c, 1.0E-9D);
    }

    public PolyaGammaDistribution(RandomGenerator rng, double b, double c, double inverseCumAccuracy) throws NotStrictlyPositiveException {
        super(rng);
        if (b <= 0) {
            throw new NotStrictlyPositiveException(LocalizedFormats.SHAPE, b);
        }
        this.b = b;
        this.c = c;
        this.solverAbsoluteAccuracy = inverseCumAccuracy;
        this.approxSampler = new ApproxSamplerWrapper(b, c, this.random);
        this.approxValue = new ApproxValueLaplace(b, c);
    }

    public double density(double x) {
        return approxValue.density(x);
    }

    public double cumulativeProbability(double v) {
        return approxValue.cumulativeProbability(v);
    }

    protected double getSolverAbsoluteAccuracy() {
        return this.solverAbsoluteAccuracy;
    }

    public double getNumericalMean() {
        return approxValue.mean();
    }

    public double getNumericalVariance() {
        return approxValue.variance();
    }

    public double getSupportLowerBound() {
        return 0;
    }

    public double getSupportUpperBound() {
        return Double.POSITIVE_INFINITY;
    }

    public boolean isSupportLowerBoundInclusive() {
        return true;
    }

    public boolean isSupportUpperBoundInclusive() {
        return false;
    }

    public boolean isSupportConnected() {
        return true;
    }

    public double sample() {
        return approxSampler.sample();
    }
}