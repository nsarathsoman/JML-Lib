package kseg;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class TestStat {


    @Test
    public void testStat() {
        List<Integer> vector = Arrays.asList(1,2,3,4,5,6,200,4,4,4,5,5,5);
        List<Integer> x = Arrays.asList(1,2,3,4,5,6);
        List<Integer> y = Arrays.asList(6,5,4,3,2,1);

        System.out.println("------Mean------");
        double mean = Stat.mean(vector);
        System.out.println(mean);
        System.out.println();

        System.out.println("------MedianOdd------");
        double median = Stat.median(vector);
        System.out.println(median);
        System.out.println();

        System.out.println("------MedianEven-----");
        double medianEven = Stat.median(x);
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

        System.out.println("------Range------");
        double range = Stat.range(vector);
        System.out.println(range);
        System.out.println();

        System.out.println("------DeviationFromMean------");
        List<Double> deMean = Stat.deviationFromMean(x);
        System.out.println(deMean);
        System.out.println();

        System.out.println("------Variance------");
        double variance = Stat.variance(x);
        System.out.println(variance);
        System.out.println();

        System.out.println("------StdDeviation------");
        double stdDeviation = Stat.stdDeviation(x);
        System.out.println(stdDeviation);
        System.out.println();

        System.out.println("------InterQuartileRange------");
        double interQuartileRande = Stat.interQuartileRange(x);
        System.out.println(interQuartileRande);
        System.out.println();

        System.out.println("------Covariance-x-->x------");
        double covariance = Stat.covariance(x, x);
        System.out.println(covariance);
        System.out.println();

        System.out.println("------Covariance-x-->y------");
        double covarianceXY = Stat.covariance(x, y);
        System.out.println(covarianceXY);
        System.out.println();

        System.out.println("------Correlation-x-->x------");
        double correlation = Stat.correlation(x, x);
        System.out.println(correlation);
        System.out.println();

        System.out.println("------Correlation-x-->y------");
        double correlationXY = Stat.correlation(x, y);
        System.out.println(correlationXY);
        System.out.println();

        System.out.println("------Correlation Zero------");
        double correlationZer = Stat.correlation(Arrays.asList(1,1), Arrays.asList(1,1));
        System.out.println(correlationZer);
        System.out.println();

        System.out.println("------Correlation-2 x 1------");
        System.out.println(Stat.correlation(Arrays.asList(4,2), Arrays.asList(10,20)));
        System.out.println();
    }
}
