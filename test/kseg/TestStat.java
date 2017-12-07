package kseg;

import kseg.util.Pair;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class TestStat {


    @Test
    public void testStat() {
        List<Integer> vector = Arrays.asList(1,2,3,4,5,6,200,4,4,4,5,5,5);

        System.out.println("------Mean------");
        double mean = Stat.mean(vector);
        System.out.println(mean);
        System.out.println();

        System.out.println("------MedianOdd------");
        double median = Stat.median(vector);
        System.out.println(median);
        System.out.println();

        System.out.println("------MedianEven-----");
        double medianEven = Stat.median(Arrays.asList(1,2,3,4,5,6));
        System.out.println(medianEven);
        System.out.println();

        System.out.println("------Mode------");
        double mode = Stat.mode(vector);
        System.out.println(mode);
        System.out.println();

        System.out.println("------Quantile------");
        double quantile = Stat.quantile(vector, .50f);
        System.out.println(quantile);
        System.out.println();
    }
}
