# polya-gamma
![pgb0](figs/pgb0.png) ![pg1c](figs/pg1c.png)

An efficient sampler of Polya-Gamma distribution in pure Java.

It is also compatible to other distributions of [Apache Commons Math3](https://commons.apache.org/proper/commons-math/).

## Example 
```java
// use default sampler
PolyaGammaDistribution pg1 = new PolyaGammaDistribution(2, 4);
double x = pg1.sample(); 
double mean = pg1.getNumericalMean();
double vari = pg1.getNumericalVariance();

// select sampler explicitly
PolyaGammaDistribution pg2 = new PolyaGammaDistribution(2, 4, PolyaGammaDistribution.AvailableSampler.Gaussian);
```

## Samplers
- Wrapper (default)
- SumGamma approx
- Gaussian approx
- Saddle Point approx (WIP)

## refs
- Polson, Nicholas G., James G. Scott, and Jesse Windle. "Bayesian inference for logistic models using Pólya–Gamma latent variables." Journal of the American statistical Association (2013)
- J. Windle, N. G. Polson, and J. G. Scott. "Improved Polya-gamma sampling". Technical Report, University of Texas at Austin (2013)
