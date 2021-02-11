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
        return b / 2 / (c + 1e-6) * Math.tanh((c + 1e-6) / 2);
    }

    public double variance() {
        return b / 4 / Math.pow((c + 1e-6), 3) * (Math.sinh((c + 1e-6)) - (c + 1e-6)) / Math.pow(Math.cosh((c + 1e-6) / 2), 2);
    }

    public double density(double x) {
        throw new MathRuntimeException(LocalizedFormats.UNSUPPORTED_OPERATION);
    }

    public double cumulativeProbability(double v) {
        throw new MathRuntimeException(LocalizedFormats.UNSUPPORTED_OPERATION);
    }
}
