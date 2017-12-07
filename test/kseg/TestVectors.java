package kseg;

import kseg.util.Utils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class TestVectors {

    @Test
    public void testVectors() {
        List<Integer> x = Arrays.asList(1,2,3);
        List<Integer> y = Arrays.asList(3,2,1);

        System.out.println("------Add------");
        List<Double> sum = Vectors.add(x, y);
        peek(sum);

        System.out.println("------Substract------");
        List<Double> diff = Vectors.subtract(x, y);
        peek(diff);

        System.out.println("------Sum------");
        List<Double> vectorsSum = Vectors.sum(Arrays.asList(x, y, x));
        peek(vectorsSum);

        System.out.println("------ScalarMultiply------");
        List<Double> scalarMultiplied = Vectors.scalarMultiply(10, x);
        peek(scalarMultiplied);

        System.out.println("------Mean------");
        double mean = Vectors.mean(Arrays.asList(1,2,3,4,5,6,7,8,9,10));
        System.out.println(mean);
        System.out.println();

        System.out.println("------Dot------");
        double dotProduct = Vectors.dot(x, y);
        System.out.println(dotProduct);
        System.out.println();

        System.out.println("------SumOfSquares------");
        double sumOfSquares = Vectors.sumOfSquares(x);
        System.out.println(sumOfSquares);
        System.out.println();

        System.out.println("------Magnitude------");
        double magnitude = Vectors.magnitude(Arrays.asList(3,4));
        System.out.println(magnitude);
        System.out.println();

        System.out.println("------SquaredDistance------");
        double squaredDistance = Vectors.squaredDistance(x, y);
        System.out.println(squaredDistance);
        System.out.println();

        System.out.println("------Distance------");
        double distance = Vectors.distance(x, y);
        System.out.println(distance);
        System.out.println();
    }

    private void peek(List<Double> vector) {
        Utils.peek(vector);
    }
}
