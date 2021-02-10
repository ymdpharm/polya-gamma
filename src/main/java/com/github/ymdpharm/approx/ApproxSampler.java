package com.github.ymdpharm.approx;

import org.apache.commons.math3.random.RandomGenerator;

public interface ApproxSampler {
    double sample(RandomGenerator rng);
}
