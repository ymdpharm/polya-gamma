package com.github.ymdpharm.polyagamma;

import com.github.ymdpharm.polyagamma.approx.*;
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

    public enum AvailableSampler {
        SumGamma,
        Gaussian,
        //SP,
        Wrapper,
    }

    public PolyaGammaDistribution(double b, double c) throws NotStrictlyPositiveException {
        this(new Well19937c(), b, c, 1.0E-9D, AvailableSampler.Wrapper);
    }

    public PolyaGammaDistribution(double b, double c, AvailableSampler availableSampler) throws NotStrictlyPositiveException {
        this(new Well19937c(), b, c, 1.0E-9D, availableSampler);
    }

    public PolyaGammaDistribution(double b, double c, double inverseCumAccuracy) throws NotStrictlyPositiveException {
        this(new Well19937c(), b, c, inverseCumAccuracy, AvailableSampler.Wrapper);
    }

    public PolyaGammaDistribution(RandomGenerator rng, double b, double c) throws NotStrictlyPositiveException {
        this(rng, b, c, 1.0E-9D, AvailableSampler.Wrapper);
    }

    public PolyaGammaDistribution(RandomGenerator rng, double b, double c, double inverseCumAccuracy, AvailableSampler availableSampler) throws NotStrictlyPositiveException {
        super(rng);
        if (b <= 0) {
            throw new NotStrictlyPositiveException(LocalizedFormats.SHAPE, b);
        }
        this.b = b;
        this.c = c;
        this.solverAbsoluteAccuracy = inverseCumAccuracy;

        switch (availableSampler) {
            case SumGamma:
                this.approxSampler = new ApproxSamplerSumGamma(b, c, rng, 100);
                break;
            case Gaussian:
                this.approxSampler = new ApproxSamplerGaussian(b, c, rng);
                break;
            //case SP:
            //    this.approxSampler = new ApproxSamplerSP(b, c, rng);
            //    break;
            default:
                this.approxSampler = new ApproxSamplerWrapper(b, c, rng);
                break;
        }
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
