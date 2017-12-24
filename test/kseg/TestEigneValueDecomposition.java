package kseg;

import kseg.util.Pair;
import kseg.util.Triplet;

public class TestEigneValueDecomposition {

    public static void main(String[] args) {

        double symetricMatrix[][] = {
                {1, 1, 0.5},
                {1, 1, 0.25},
                {0.5, 0.25, 2}
        };

        Matrix matrix = new Matrix(symetricMatrix);

        System.out.println("------PrincipalValueOfTheta------");
        Triplet<Double, Integer, Integer> largestOffDiagonalElement = matrix.largestOffDiagonalElement();
        double theta = new EigenValueDecomposition().principalValueOfTheta(matrix, largestOffDiagonalElement.getX2(), largestOffDiagonalElement.getX3());
        System.out.println(theta);
        System.out.println();

        System.out.println("------EVD------");
        Pair<Matrix, Matrix> valueVector = matrix.evd();
        valueVector.getLeft().dumpToConsole();
        System.out.println();
        valueVector.getRight().dumpToConsole();

        System.out.println("------EVD II------");
        symetricMatrix = new double[][]{
                {1, Math.sqrt(2), 2},
                {Math.sqrt(2), 3, Math.sqrt(2)},
                {2, Math.sqrt(2), 1}
        };
        matrix = new Matrix(symetricMatrix);
        valueVector = matrix.evd();
        valueVector.getLeft().dumpToConsole();
        System.out.println();
        valueVector.getRight().dumpToConsole();
    }
}
