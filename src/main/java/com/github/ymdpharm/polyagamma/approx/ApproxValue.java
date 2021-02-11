package com.github.ymdpharm.polyagamma.approx;

public interface ApproxValue {
    double mean();

    double variance();

    double density(double x);

    double cumulativeProbability(double v);
}
