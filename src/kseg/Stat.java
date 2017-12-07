package kseg;

import kseg.util.Pair;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static kseg.Vectors.dot;
import static kseg.Vectors.sumOfSquares;

public class Stat {

    //Measures of central tendencies
    public static <T extends Number> double mean(List<T> vector) {
        return Vectors.mean(vector);
    }

    public static <T extends Number> double median(List<T> vector) {
        int size = vector.size();

        List<T> sortedVector = vector.stream()
                .sorted()
                .collect(Collectors.toList());
        int midPt = size / 2;
        if (size % 2 == 1) {
            return sortedVector.get(midPt)
                    .doubleValue();
        } else {
            int loMidPt = midPt - 1;
            int highMidPt = midPt;
            return (sortedVector.get(loMidPt).doubleValue() + sortedVector.get(highMidPt).doubleValue()) / 2;
        }
    }

    public static <T extends Number> T mode(List<T> vector) {
        Optional<Map.Entry<T, Long>> modeEntry = vector.stream()
                .map(element -> new Pair<>(element, 1l))
                .collect(groupingBy(Pair::getLeft, summingLong(Pair::getRight)))
                .entrySet()
                .stream()
                .collect(maxBy(Comparator.comparingLong(element -> element.getValue())));

        if (modeEntry.isPresent()) {
            return modeEntry.get()
                    .getKey();
        } else {
            return null;
        }

    }

    public static <T extends Number> T quantile(List<T> vector, float percentage) {
        int size = vector.size();
        int percIndex = (int) (size * percentage);
        if (size <= percIndex) {
            throw new RuntimeException("Percentage should be less than 1");
        }
        return vector.stream()
                .sorted()
                .collect(Collectors.toList())
                .get(percIndex);
    }

    //measures of Dispersion

    public static <T extends Number> double range(List<T> vector) {
        List<T> sortedVector = vector.stream()
                .sorted()
                .collect(Collectors.toList());
        return sortedVector.get(vector.size() - 1).doubleValue() - sortedVector.get(0).doubleValue();
    }

    public static <T extends Number> List<Double> deviationFromMean(List<T> vector) {
        double mean = mean(vector);
        return vector.stream()
                .map(t -> t.doubleValue() - mean)
                .collect(Collectors.toList());
    }

    public static <T extends Number> double variance(List<T> vector) {
        return sumOfSquares(deviationFromMean(vector)) / (vector.size() - 1);
    }

    public static <T extends Number> double stdDeviation(List<T> vector) {
        return Math.sqrt(variance(vector));
    }


    //    Not affected by small number of outliers - rest of the measures of central tendencies are affected by outliers

    public static <T extends Number> double interQuartileRange(List<T> vector) {
        return quantile(vector, 0.75f).doubleValue() - quantile(vector, 0.25f).doubleValue();
    }

    //    Large +ve covariance means x tends to be large when y is large and
    //    small -ve covariance means x tends to be small when y tends to be large
    //    its hard to define the meaning of a particular covariance number

    public static <T extends Number> double covariance(List<T> x, List<T> y) {
        return dot(deviationFromMean(x), deviationFromMean(y)) / (x.size() - 1);
    }

    //    Correlation is always b/w -1(perfect anti correlation) and 1(perfect correlation) and is unitless
    public static <T extends Number> double correlation(List<T> x, List<T> y) {
        double stdX = stdDeviation(x);
        double stdy = stdDeviation(y);
        if(stdX == 0 || stdy == 0) {
            return 0;
        }
        return covariance(x, y) / stdX / stdy;
    }

}
