import com.github.ymdpharm.polyagamma.PolyaGammaDistribution;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Example {
    public static void main(String[] args) {
        PolyaGammaDistribution pg = new PolyaGammaDistribution(1.5, 4);
        List<Double> samples = IntStream.range(0, 100).mapToDouble(i -> {
            double x  = pg.sample();
            System.out.println(i + ": " + x);
            return x;
        }).boxed().collect(Collectors.toList());

        Double mean = samples.stream().mapToDouble(e -> e).average().orElse(0);
        Double vari = samples.stream().mapToDouble(a -> Math.pow(a - mean, 2)).sum() / samples.size();

        System.out.println("mean: " + mean);
        System.out.println("variance: " + vari);

    }
}
