package kseg;

import kseg.util.Triplet;

public class TestSVD {

    public static void main(String[] args) {

        double elements[][] = {
                {1, 0, 0, 0, 2},
                {0, 0, 3, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 2, 0, 0, 0}
        };

        Matrix matrix = new Matrix(elements);

        System.out.println("------SVD------");
        Triplet<Matrix, Matrix, Matrix> svd = matrix.svd();
        System.out.println("u");
        svd.getX1().dumpToConsole();
        System.out.println();
        System.out.println("SIG");
        svd.getX2().dumpToConsole();
        System.out.println();
        System.out.println("vT");
        svd.getX3().dumpToConsole();
        System.out.println();

        Matrix res =  svd.getX1().multiply(svd.getX2().multiply(svd.getX3()));
        System.out.println("Result ==");
        res.dumpToConsole();
        System.out.println();

    }
}
