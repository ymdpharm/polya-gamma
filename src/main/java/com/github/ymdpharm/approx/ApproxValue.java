package com.github.ymdpharm.approx;

public interface ApproxValue {
    double mean();

    double variance();

    double density(double x);

    double cumulativeProbability(double v);
}
