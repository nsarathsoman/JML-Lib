package kseg;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Vectors {

    public static <T extends Number> List<Double> add(List<T> x, List<T> y) {
        int size = x.size();

        if(y.size() != size) {
            throw new RuntimeException("x and y should be of equal length");
        }

        return IntStream.range(0, size)
                .mapToDouble(i -> x.get(i).doubleValue() + y.get(i).doubleValue())
                .boxed()
                .collect(Collectors.toList());
    }

    public static <T extends Number> List<Double> subtract(List<T> x, List<T> y) {
        int size = x.size();

        if(y.size() != size) {
            throw new RuntimeException("x and y should be of equal length");
        }

        return IntStream.range(0, size)
                .mapToDouble(i -> x.get(i).doubleValue() - y.get(i).doubleValue())
                .boxed()
                .collect(Collectors.toList());
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

    public static <T extends Number> double mean(List<T> x) {
        double sum = x.stream()
                .map(xi -> xi.doubleValue())
                .reduce((x1, x2) -> x1 + x2)
                .orElse(0d);

        return sum / x.size();
    }

    public static <T extends Number> double dot(List<T> x, List<T> y) {
        int size = x.size();

        if(y.size() != size) {
            throw new RuntimeException("x and y should be of equal length");
        }

        return IntStream.range(0, size)
                .mapToDouble(i -> x.get(i).doubleValue() * y.get(i).doubleValue())
                .reduce((x1, x2) -> x1 + x2)
                .orElse(0d);
    }

    public static <T extends Number> double sumOfSquares(List<T> x) {

        return x.stream()
                .map(xi -> Math.pow(xi.doubleValue(), 2))
                .reduce((x1, x2) -> x1 + x2)
                .orElse(0d);
    }

    public static <T extends Number> double magnitude(List<T> x) {

        double sumOfSquares = sumOfSquares(x);
        return Math.sqrt(sumOfSquares);
    }

    public static <T extends Number> double squaredDistance(List<T> x, List<T> y) {
        int size = x.size();

        if(y.size() != size) {
            throw new RuntimeException("x and y should be of equal length");
        }

        return sumOfSquares(subtract(x, y));
    }

    public static <T extends Number> double distance(List<T> x, List<T> y) {
        int size = x.size();

        if(y.size() != size) {
            throw new RuntimeException("x and y should be of equal length");
        }

        return Math.sqrt(sumOfSquares(subtract(x, y)));
    }

}