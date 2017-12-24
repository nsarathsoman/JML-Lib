package kseg;

import kseg.util.Pair;
import kseg.util.Triplet;

public class SingularValueDecomposition {
    public Triplet<Matrix, Matrix, Matrix> svd(Matrix a) {
        Matrix aT = a.transpose();
        Matrix aTa = aT.multiply(a);

        Pair<Matrix, Matrix> evd = aTa.evd();
        Matrix eigenValuesSquare = evd.getLeft();
        eigenValuesSquare.dumpToConsole();
        Matrix v = evd.getRight();
        v.dumpToConsole();

        double eigenMatrixElements[][] = new double[eigenValuesSquare.getNoOfRows()][eigenValuesSquare.getNoOfColumns()];
        for(int i = 0; i < eigenValuesSquare.getNoOfRows(); i++) {
            for (int j = 0; j < eigenValuesSquare.getNoOfColumns(); j++) {
                if(i == j) {
                    eigenMatrixElements[i][j] = Math.sqrt(eigenValuesSquare.getElement(i, j));
                } else {
                    eigenMatrixElements[i][j] = 0;
                }
            }
        }

        Matrix eigeMatrix = new Matrix(eigenMatrixElements);


        double maxEigenValue = eigeMatrix.getElement(0, 0);
        for(int i = 1; i < eigenValuesSquare.getNoOfColumns(); i++) {
            double val = eigeMatrix.getElement(i, i);
            if(val > maxEigenValue) {
                maxEigenValue = val;
            }
        }

        Matrix u = a.multiply(v).scalarMultiply(1/maxEigenValue);


        return new Triplet<>(u, eigeMatrix, v.transpose());
    }
}
