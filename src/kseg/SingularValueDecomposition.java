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


        for (int i = 0; i < eigenMatrixElements[0].length; i++) {
            double maxEigenValue = eigenMatrixElements[i][i];
            for (int j = i + 1; j < eigenMatrixElements[0].length; j++) {
                double val = eigenMatrixElements[j][j];
                if (val > maxEigenValue) {
                    eigenMatrixElements[j][j] = maxEigenValue;
                    eigenMatrixElements[i][i] = val;
                    v = v.swapColumns(j, i);
                    maxEigenValue = val;
                }
            }
        }

        Matrix eigeMatrix = new Matrix(eigenMatrixElements);

        Matrix u = a.multiply(v).scalarMultiply(1/eigeMatrix.getElement(0, 0));


        return new Triplet<>(u, eigeMatrix, v.transpose());
    }
}
