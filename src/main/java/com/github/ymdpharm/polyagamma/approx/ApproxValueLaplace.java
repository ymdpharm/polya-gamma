package com.github.ymdpharm.polyagamma.approx;

import org.apache.commons.math3.exception.MathRuntimeException;
import org.apache.commons.math3.exception.util.LocalizedFormats;

public class ApproxValueLaplace implements ApproxValue {
    private final double b;
    private final double c;

    public ApproxValueLaplace(double b, double c) {
        this.b = b;
        this.c = c;
    }

    public double mean() {
        return b / 2 / c * Math.tanh(c / 2);
    }

    public double variance() {
        return b / 4 / Math.pow(c, 3) * (Math.sinh(c) - c) / Math.pow(Math.cosh(c / 2), 2);
    }

    public double density(double x) {
        throw new MathRuntimeException(LocalizedFormats.UNSUPPORTED_OPERATION);
    }

    public double cumulativeProbability(double v) {
        throw new MathRuntimeException(LocalizedFormats.UNSUPPORTED_OPERATION);
    }
}
