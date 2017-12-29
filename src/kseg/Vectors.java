package kseg;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class Vectors {

    public static <T extends Number> List<Double> add(List<T> x, List<T> y) {
        int size = x.size();

        if (y.size() != size) {
            throw new RuntimeException("x and y should be of equal length");
        }

        return IntStream.range(0, size)
                .mapToDouble(i -> x.get(i).doubleValue() + y.get(i).doubleValue())
                .boxed()
                .collect(Collectors.toList());
    }

    public static <T extends Number> List<Double> subtract(List<T> x, List<T> y) {
        int size = x.size();

        if (y.size() != size) {
            throw new RuntimeException("x and y should be of equal length");
        }

        return IntStream.range(0, size)
                .mapToDouble(i -> x.get(i).doubleValue() - y.get(i).doubleValue())
                .boxed()
                .collect(Collectors.toList());
    }

    public static double[] subtract(double[] x, double[] y) {
        int size = x.length;

        if (y.length != size) {
            throw new RuntimeException("x and y should be of equal length");
        }

        return IntStream.range(0, size)
                .mapToDouble(i -> x[i] - y[i])
                .toArray();
    }

    public static <T extends Number> List<Double> sum(List<List<T>> x) {

        return x.stream()
                .map(Vectors::transformToDoubleList)
                .reduce((v1, v2) -> Vectors.add(v1, v2))
                .get();
    }

    private static <T extends Number> List<Double> transformToDoubleList(List<T> vector) {
        return vector.stream()
                .map(t -> t.doubleValue())
                .collect(Collectors.toList());
    }

    public static <T extends Number> List<Double> scalarMultiply(T scalar, List<T> x) {
        return x.stream()
                .map(xi -> xi.doubleValue() * scalar.doubleValue())
                .collect(Collectors.toList());
    }

    public static <T extends Number> double avg(List<T> x) {
        double sum = x.stream()
                .map(xi -> xi.doubleValue())
                .reduce((x1, x2) -> x1 + x2)
                .orElse(0d);

        return sum / x.size();
    }

    public static double avg(double[] x) {
        double sum = DoubleStream.of(x)
                .reduce((x1, x2) -> x1 + x2)
                .orElse(0d);

        return sum / x.length;
    }

    public static <T extends Number> double dot(List<T> x, List<T> y) {
        int size = x.size();

        if (y.size() != size) {
            throw new RuntimeException("x and y should be of equal length");
        }

        return IntStream.range(0, size)
                .mapToDouble(i -> x.get(i).doubleValue() * y.get(i).doubleValue())
                .reduce((x1, x2) -> x1 + x2)
                .orElse(0d);
    }

    public static double dot(double x[], double y[]) {
        int size = x.length;

        if (y.length != size) {
            throw new RuntimeException("x and y should be of equal length");
        }

        return IntStream.range(0, size)
                .mapToDouble(i -> x[i] * y[i])
                .reduce((x1, x2) -> x1 + x2)
                .orElse(0d);
    }

    public static <T extends Number> double sumOfSquares(List<T> x) {

        return x.stream()
                .map(xi -> Math.pow(xi.doubleValue(), 2))
                .reduce((x1, x2) -> x1 + x2)
                .orElse(0d);
    }

    public static double sumOfSquares(double x[]) {
        return DoubleStream.of(x)
                .map(xi -> Math.pow(xi, 2))
                .reduce((x1, x2) -> x1 + x2)
                .orElse(0d);
    }

    public static <T extends Number> double magnitude(List<T> x) {

        double sumOfSquares = sumOfSquares(x);
        return Math.sqrt(sumOfSquares);
    }

    public static double magnitude(double x[]) {
        double sumOfSquares = sumOfSquares(x);
        return Math.sqrt(sumOfSquares);
    }

    public static <T extends Number> double squaredDistance(List<T> x, List<T> y) {
        int size = x.size();

        if (y.size() != size) {
            throw new RuntimeException("x and y should be of equal length");
        }

        return sumOfSquares(subtract(x, y));
    }

    public static <T extends Number> double distance(List<T> x, List<T> y) {
        int size = x.size();

        if (y.size() != size) {
            throw new RuntimeException("x and y should be of equal length");
        }

        return Math.sqrt(sumOfSquares(subtract(x, y)));
    }

    public static double distance(double[] x, double[] y) {
        int size = x.length;

        if (y.length != size) {
            throw new RuntimeException("x and y should be of equal length");
        }

        return Math.sqrt(sumOfSquares(subtract(x, y)));
    }

    public static double cosineDistance(double[] x, double[] y) {
        int size = x.length;

        if (y.length != size) {
            throw new RuntimeException("x and y should be of equal length");
        }

        return dot(x, y) / (magnitude(x) * magnitude(y));
    }

    public static double distance(double[] x, double[] y, DistanceAlgorithm distanceAlgorithm) {
        switch (distanceAlgorithm) {
            case EUCLIDEAN:
                return distance(x, y);
            case COSINE:
                return cosineDistance(x, y);
            case PEARSON_CORRELATION:
                return Stat.correlation(x, y);
            default:
                throw new RuntimeException("Unknown distance algo");
        }
    }

    public static double similarity(double[] x, double[] y, DistanceAlgorithm distanceAlgorithm) {
        double distance = distance(x, y, distanceAlgorithm);
        switch (distanceAlgorithm) {
            case EUCLIDEAN:
                return 1 / (1 + distance);
            case COSINE:
            case PEARSON_CORRELATION:
                return 0.5 + 0.5 * distance;
            default:
                throw new RuntimeException("Unknown distance algo");
        }
    }

    public static double[] toUnitVector(double[] column) {
        double magnitude = magnitude(column);

        return 0 == magnitude ? column : DoubleStream.of(column)
                .map(c -> c / magnitude)
                .toArray();
    }

    public enum DistanceAlgorithm {
        EUCLIDEAN, COSINE, PEARSON_CORRELATION
    }
}
