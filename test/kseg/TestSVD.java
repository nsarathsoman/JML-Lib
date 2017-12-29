package kseg;

import Jama.SingularValueDecomposition;
import kseg.util.Triplet;

import java.util.stream.DoubleStream;

public class TestSVD {

    public static void main(String[] args) {

//        double elements[][] = {
//                {1, 0, 0, 0, 2},
//                {0, 0, 3, 0, 0},
//                {0, 0, 0, 0, 0},
//                {0, 2, 0, 0, 0}
//        };

//        double elements[][] = {
//                {1, 0, 0, 0, 2},
//                {0, 0, 3, 0, 0},
//                {0, 0, 0, 0, 0},
//                {0, 2, 0, 0, 0},
//                {1, 4, 3, 0, 3},
//                {4, 4, 3, 0, 3}
//        };

        double elements[][] = {
                {2, -2, 1},
                {5, 1, 4},
        };

        Matrix matrix = new Matrix(elements);

        System.out.println("------SVD------");
        Triplet<Matrix, Matrix, Matrix> svd = matrix.svd();
        System.out.println("u");
        svd.getX1().dumpToConsole();
        System.out.println();

        System.out.println("u*uT");
        svd.getX1().multiply(svd.getX1().transpose()).dumpToConsole();
        System.out.println();

        System.out.println("SIG");
        svd.getX2().dumpToConsole();
        System.out.println();

        System.out.println("v");
        svd.getX3().dumpToConsole();
        System.out.println();

        System.out.println("v*vT");
        svd.getX3().multiply(svd.getX3().transpose()).dumpToConsole();
        System.out.println();

        System.out.println("Result ==");
        svd.getX1().multiply(svd.getX2().multiply(svd.getX3().transpose())).dumpToConsole();
        System.out.println();

        //
        System.out.println("-----JAMA---");
        Jama.Matrix matrix1 = Jama.Matrix.constructWithCopy(elements);
        SingularValueDecomposition singularValueDecomposition = matrix1.svd();
        DoubleStream.of(singularValueDecomposition.getSingularValues())
                .forEach(System.out::println);
        System.out.println();

        double jU[][] = singularValueDecomposition.getU().getArray();
        for(int i = 0; i < jU.length; i++) {
            for(int j = 0; j < jU[0].length; j++) {
                System.out.print("  " + jU[i][j] + "  |");
            }
            System.out.println();
        }
        System.out.println();

        double jV[][] = singularValueDecomposition.getV().getArray();

        for(int i = 0; i < jV.length; i++) {
            for(int j = 0; j < jV[0].length; j++) {
                System.out.print("  " + jV[i][j] + "  |");
            }
            System.out.println();
        }
        System.out.println();

    }
}
