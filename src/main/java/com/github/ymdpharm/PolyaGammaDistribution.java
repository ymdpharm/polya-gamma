package com.github.ymdpharm;

import com.github.ymdpharm.approx.ApproxSampler;
import com.github.ymdpharm.approx.ApproxSamplerSP;
import com.github.ymdpharm.approx.ApproxValue;
import com.github.ymdpharm.approx.ApproxValueLaplace;
import org.apache.commons.math3.distribution.AbstractRealDistribution;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;
import org.apache.commons.math3.exception.MathRuntimeException;

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
        this.approxSampler = new ApproxSamplerSP(b, c);
        this.approxValue = new ApproxValueLaplace(b, c);
    }

    public double density(double x) {
        throw new MathRuntimeException(LocalizedFormats.UNSUPPORTED_OPERATION);
    }

    public double cumulativeProbability(double v) {
        throw new MathRuntimeException(LocalizedFormats.UNSUPPORTED_OPERATION);
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
        return Double.MAX_VALUE;
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
