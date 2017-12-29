package kseg;

import kseg.util.Pair;
import kseg.util.Triplet;

import java.util.Stack;

public class EVD {

    public double principalValueOfTheta(Matrix matrix, int i, int k) {
        double aii = matrix.getElement(i, i);
        double akk = matrix.getElement(k, k);
        double aik = matrix.getElement(i, k);

        if (aii == aik) {
            if (aik > 0) {
                return Math.PI / 4;
            } else if (aik < 0) {
                return -Math.PI / 4;
            }
            return 0;
//            throw new RuntimeException("aik = 0, not supported");
        } else {
//            if (aii == akk) {
//                return Math.PI / 4;
//            }
            double theta = 0.5 * Math.atan((2 * aik) / (aii - akk));
            if((theta < -Math.PI/ 4) || (theta > Math.PI / 4)) {
                return Math.PI / 4;
            } else {
                return theta;
            }
        }
    }

    public Pair<Matrix, Matrix> evd(final Matrix matrix) {

        //TODO add check for symmetric property

        Stack<Matrix> rotationMatrices = new Stack<>();
        Matrix b = matrix;

        int iteration = 0;

        while (iteration < matrix.colDimensions()) {
            Matrix s = buildSMatrix(b);
            Matrix sT = s.transpose();
            rotationMatrices.push(s);
            b = sT.multiply(b.multiply(s));
            iteration++;
        }

        Matrix eigenVectors = rotationMatrices.pop();

        while (!rotationMatrices.empty()) {
            eigenVectors = rotationMatrices.pop().multiply(eigenVectors);
        }

//        b = b.roundOff();
//        eigenVectors = eigenVectors.roundOff();

        return new Pair<>(b, eigenVectors);

    }

    private Matrix buildSMatrix(Matrix matrix) {
        Triplet<Double, Integer, Integer> largestOffDiagonal = matrix.largestOffDiagonalElement();
        double theta = principalValueOfTheta(matrix, largestOffDiagonal.getX2(), largestOffDiagonal.getX3());

        double s[][] = new double[matrix.rowDimensions()][matrix.colDimensions()];

        for (int i = 0; i < matrix.rowDimensions(); i++) {
            for (int j = 0; j < matrix.colDimensions(); j++) {
                if (i == largestOffDiagonal.getX2() && j == largestOffDiagonal.getX2()) {
                    s[i][j] = Math.cos(theta);
                } else if (i == largestOffDiagonal.getX2() && j == largestOffDiagonal.getX3()) {
                    s[i][j] = -Math.sin(theta);
                } else if (i == largestOffDiagonal.getX3() && j == largestOffDiagonal.getX2()) {
                    s[i][j] = Math.sin(theta);
                } else if (i == largestOffDiagonal.getX3() && j == largestOffDiagonal.getX3()) {
                    s[i][j] = Math.cos(theta);
                } else if (i == j) {
                    s[i][j] = 1;
                } else {
                    s[i][j] = 0;
                }
            }
        }

        return new Matrix(s);
    }
}
