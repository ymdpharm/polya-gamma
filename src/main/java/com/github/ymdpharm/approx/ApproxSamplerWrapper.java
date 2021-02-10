package com.github.ymdpharm.approx;

import org.apache.commons.math3.random.RandomGenerator;

public class ApproxSamplerWrapper implements ApproxSampler {
    private final double b;
    private final int bint;
    private final double c;
    private final ApproxSampler dev;
    private final ApproxSampler devInt;
    private final ApproxSampler sp;

    public ApproxSamplerWrapper(double b, double c) {
        this.b = b;
        this.bint = Math.round((float) b);
        this.c = c;
        this.dev = new ApproxSamplerDevroye(b, c, 100);
        this.devInt = new ApproxSamplerDevroyeInt(bint, c);
        this.sp = new ApproxSamplerSP(b, c);
    }

    public double sample(RandomGenerator rng) {
//        todo: fasten.
//        if (b > 13) {
//            return sp.sample(rng);
//        } else if (Math.abs(b - bint) < 1e-6 && bint > 0) {
//            return devInt.sample(rng);
//        } else if (b > 0) {
//            return dev.sample(rng);
//        } else {
//            return 0;
//        }

        if (b > 0) {
            return dev.sample(rng);
        } else {
            return 0;
        }
    }
}
