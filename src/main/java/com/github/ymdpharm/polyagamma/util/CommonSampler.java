package com.github.ymdpharm.polyagamma.util;

import com.github.ymdpharm.polyagamma.exception.MaxIterationException;
import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.util.FastMath;

public class CommonSampler {
    private final RandomGenerator rng;
    private final ExponentialDistribution exp1;
    private final NormalDistribution norm01;

    public CommonSampler(RandomGenerator rng) {
        this.rng = rng;
        this.exp1 = new ExponentialDistribution(rng, 1);
        this.norm01 = new NormalDistribution(rng, 0, 1);
    }

    public double leftTGamma(double shape, double rate, double trunc) {
        // sample from left truncated gamma
        double a = shape;
        double b = rate * trunc;

        if (trunc <= 0 || shape < 1) {
            return 0;
        } else if (shape == 1) {
            return exp1.sample() / rate + trunc;
        }

        double d1 = b - a;
        double d3 = a - 1;
        double c0 = 0.5 * (d1 + FastMath.sqrt(d1 * d1 + 4 * b)) / b;

        double x;

        while (true) {
            x = b + exp1.sample() / c0;
            double lRho = d3 * FastMath.log(x) - x * (1 - c0);
            double lM = d3 * FastMath.log(d3 / (1 - c0)) - d3;

            if (FastMath.log(rng.nextDouble()) <= (lRho - lM)) {
                return trunc * x / b;
            }
        }
    }

    public double tInvGauss(double mu, double lambda, double trunc) {
        // sample form truncated inv gauss
        double x = trunc + 1.0;
        if (trunc < mu) {
            double alpha = 0;
            while (rng.nextDouble() > alpha) {
                x = tInvChiSq(lambda, trunc);
                alpha = FastMath.exp(-0.5 * lambda / FastMath.pow(mu, 2) * x);
            }
        } else {
            while (x > trunc) {
                x = invGauss(mu, lambda);
            }
        }
        return x;

    }

    private double invGauss(double mu, double lambda) {
        // sample from inv gauss
        double x = rng.nextGaussian();
        double y = FastMath.pow(x, 2) * mu;
        double w = mu - (0.5 * mu / lambda) * (FastMath.sqrt(y * (y + 4 * lambda)) - y);
        if (rng.nextDouble() < mu / (mu + w)) {
            return w;
        } else {
            return FastMath.pow(mu, 2) / w;
        }
    }

    private double tInvChiSq(double scale, double trunc) {
        // sample from truncated inv ChiSquared
        double r = trunc / scale;
        double e = tNorm(1 / FastMath.sqrt(r));
        double x = scale / (FastMath.pow(e, 2));
        return x;
    }

    private double tNorm(double left) {
        // sample from standard truncated Normal dist
        int maxIter = 1000;
        if (left < 0) {
            while (true) {
                double x = norm01.sample();
                if (x > left) {
                    return x;
                }
            }
        } else {
            // approx
            double astar = 0.5 * (left + FastMath.sqrt(left * left + 4));
            ExponentialDistribution exp = new ExponentialDistribution(rng, 1 / astar);
            for (int iter = 0; iter < maxIter; iter++) {
                double x = exp.sample() + left; // tExp
                double rho = FastMath.exp(-0.5 * FastMath.pow((x - astar), 2));
                if (rng.nextDouble() < rho) {
                    return x;
                }
            }
            throw new MaxIterationException("Reached the upper iteration limit of:" + maxIter);
        }
    }
}
