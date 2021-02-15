package com.github.ymdpharm.polyagamma.util;

import org.apache.commons.math3.special.Erf;
import org.apache.commons.math3.util.FastMath;

public class CommonFunction {
    public static double[] newtonMethodWithK1K2Info(double md, double x0) {
        double tol = 1e-10;
        double x = 0;
        double k1 = 0;
        double k2 = 0;

        for (int i = 0; i < 100; i++) {
            k1 = cgfP1(x0);
            k2 = cgfP2(x0, k1);

            if (FastMath.abs(k2) < tol) {
                return new double[]{x, k1, k2};
            }
            x = x0 - (k1 - md) / k2;
            if (FastMath.abs(x - x0) <= tol) {
                return new double[]{x, k1, k2};
            }
            x0 = x;
        }
        return new double[]{x, k1, k2};
    }

    public static double newtonMethod(double md, double x0) {
        return newtonMethodWithK1K2Info(md, x0)[0];
    }

    public static double seed(double x) {
        if (x == 1) {
            return 0;
        } else if (x > 1) {
            return 1;
        } else {
            return -1.5;
        }
    }

    // M(t) in the neighborhood of 0
    public static double mgf(double u, double z) {
        if (z == 0) {
            return 0;
        } else if (u == 0) {
            return FastMath.cosh(z);
        } else if (u > 0) {
            return FastMath.cosh(z) / FastMath.cos(FastMath.sqrt(2 * u));
        } else {
            return FastMath.cosh(z) / FastMath.cosh(FastMath.sqrt(-2 * u));
        }
    }

    // K'(t) in the neighborhood of 0
    public static double cgfP1(double u) {
        return CommonFunction.y(2 * u);
    }

    // K''(t) in the neighborhood of 0
    public static double cgfP2(double u, double cgfP1) {
        return FastMath.pow(cgfP1, 2) + (1 - cgfP1) / 2 / u;
    }

    // cum probability of inv gauss at x
    public static double pInvGauss(double x, double mu, double lambda) {
        double z = 1 / mu;
        double b = FastMath.sqrt(lambda / x) * (x * z - 1);
        double a = FastMath.sqrt(lambda / x) * (x * z + 1) * -1;
        double y = pNorm(b, false) + FastMath.exp(2 * lambda * z + pNorm(a, true));
        return y;
    }

    // cum probability of gauss at x
    public static double pNorm(double x, boolean log) {
        double p;
        if (FastMath.abs(x) > 40.0D) {
            p = x < 0.0D ? 0.0D : 1.0D;
        } else {
            p = 0.5D * Erf.erfc(-x / FastMath.sqrt(2));
        }

        if (log) {
            return FastMath.log(p);
        } else {
            return p;
        }
    }

    // mode of phi
    public static double y(double v) {
        double tol = 1e-6;
        double r = FastMath.sqrt(Math.abs(v));

        if (v > tol) {
            return FastMath.tan(r) / r;
        } else if (v < -tol) {
            return FastMath.tanh(r) / r;
        } else {
            return 1;
        }
    }

    // Saddlepoint approximation for phi distribution.
    public static double spApprox(double x, double b, double z, double coef) {
        double[] result = newtonMethodWithK1K2Info(x, seed(x));
        double u = result[0];
        double k2 = result[2];
        double t = u + 0.5 * z * z;
        return coef * FastMath.pow(mgf(u, z), b) * FastMath.exp(-b * t * x) / FastMath.sqrt(k2);
    }
}
