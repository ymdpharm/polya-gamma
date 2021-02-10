package com.github.ymdpharm;

public class Boot {
    public static void main(String[] args) {
        PolyaGammaDistribution pg = new PolyaGammaDistribution(20, 4);
        for(int i = 0; i < 100 ; i++) {
            double v = pg.sample();
            System.out.println(v);
        }
    }
}
