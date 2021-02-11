import com.github.ymdpharm.polyagamma.PolyaGammaDistribution;

public class Example {
    public static void main(String[] args) {
        PolyaGammaDistribution pg = new PolyaGammaDistribution(1.5, 4);
        for(int i = 0; i < 100 ; i++) {
            double v = pg.sample();
            System.out.println(v);
        }
    }
}
