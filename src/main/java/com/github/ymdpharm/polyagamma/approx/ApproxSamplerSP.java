package com.github.ymdpharm.polyagamma.approx;

import com.github.ymdpharm.polyagamma.exception.MaxIterationException;
import com.github.ymdpharm.polyagamma.util.CommonFunction;
import com.github.ymdpharm.polyagamma.util.CommonSampler;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.special.Gamma;
import org.apache.commons.math3.util.FastMath;

public class ApproxSamplerSP implements ApproxSampler {
    private final double b;
    private final double c;
    private final int maxIter = 1000;
    private final RandomGenerator rng;
    private final CommonSampler commonSampler;

    private double z;
    private double xl;
    private double xc;
    private double xr;
    private double ul;
    private double uc;
    private double ur;
    private double tr;
    private double l1l;
    private double l1r;
    private double eIceptl;
    private double eIceptr;
    private double alphar;
    private double alphal;
    private double coefl;
    private double coefr;
    private double p, q;

    /**
     * pick one from PG(b,c) using saddle point approximation.
     * b must be >= 1.
     */
    public ApproxSamplerSP(double b, double c, RandomGenerator rng) {
        if (b < 1) {
            throw new NumberIsTooSmallException(LocalizedFormats.SHAPE, b, 1, false);
        }
        this.b = b;
        this.c = c;
        this.rng = rng;
        this.commonSampler = new CommonSampler(rng);
        // X ~ PG(b, c) <=> 4X ~ J*(b, c/2)
        this.z = FastMath.abs(c);
        double z2 = FastMath.pow(z, 2);
        if (z < 10e-6) {
            this.xl = 1;
        } else {
            this.xl = FastMath.tanh(z) / z;
        }
        this.xc = xl * 2.75;
        this.xr = xl * 3;

        this.ul = -0.5 * z2;
        this.uc = CommonFunction.newtonMethod(xc, CommonFunction.seed(xc));
        this.ur = CommonFunction.newtonMethod(xr, CommonFunction.seed(xr));

        this.tr = ur + 0.5 * z2;

        this.l1l = -0.5 / xl / xl;
        this.l1r = -tr - 1 / xr;

        this.eIceptl = CommonFunction.mgf(ul, z) + FastMath.exp(-0.5 / xc + 1. / xl);
        this.eIceptr = CommonFunction.mgf(ur, z) + FastMath.exp(1 - FastMath.log(xr) + FastMath.log(xc));

        this.alphar = 1 + 0.5 / FastMath.pow(xc, 2) * (1 - xc) / uc;
        this.alphal = alphar / xc;

        this.coefl = FastMath.sqrt(b / FastMath.PI / 2) / FastMath.sqrt(alphal);
        this.coefr = FastMath.sqrt(b / FastMath.PI / 2) / FastMath.sqrt(alphar);

        this.p = 1 / FastMath.sqrt(alphal) * FastMath.pow(eIceptl, b) * FastMath.exp(b * (0.5 / xc - FastMath.sqrt(-2 * l1l)))
                * CommonFunction.pInvGauss(xc, 1 / Math.sqrt(-2 * l1l), b);
        this.q = coefr * FastMath.pow(eIceptr, b) * FastMath.exp(b * (-FastMath.log(-b * l1r)) + Gamma.logGamma(b))
                * Gamma.regularizedGammaQ(b, (-b * l1r) * xc); // todo: fix: sometimes it reaches to 0, and dist seems to be wrong.
    }

    public double sample() {
        double ratio = p / (p + q);

        double x;
        double f;
        int iter = 0;

        while (iter < maxIter) {
            iter += 1;

            if (rng.nextDouble() < ratio) {
                x = commonSampler.tInvGauss(1. / FastMath.log(-2 * l1l), b, xc);
                f = coefl * FastMath.pow(eIceptl, b) * FastMath.exp(0.5 * b * xc - 1.5 * FastMath.log(x) - 0.5 * b / x + b * (l1l * x));
            } else {
                x = commonSampler.leftTGamma(b, -b * l1r, xc);
                f = coefr * eIceptr * FastMath.exp(b * FastMath.log(xc) + (l1r * x) + (b - 1) * FastMath.log(x));
            }

            double spa = CommonFunction.spApprox(x, b, z, FastMath.sqrt(b / FastMath.PI / 2)); // todo: あってる…？

            // System.out.println(b * 0.25 * x + "," + spa + "," + f);
            if (rng.nextDouble() < spa / f) {
                return b * 0.25 * x;
            }
        }

        throw new MaxIterationException("Reached the upper iteration limit of: " + maxIter);
    }
}
