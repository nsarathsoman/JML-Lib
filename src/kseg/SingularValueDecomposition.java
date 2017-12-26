package kseg;

import kseg.util.Pair;
import kseg.util.Triplet;

public class SingularValueDecomposition {
    public Triplet<Matrix, Matrix, Matrix> svd(Matrix a) {
        Matrix aT = a.transpose();
        Matrix aTa = aT.multiply(a);

        Pair<double[], Matrix> eigenValuesV = sortEigenValuesAndVectors(aTa.evd());
        double[] eigenValues = eigenValuesV.getLeft();

        Matrix v = eigenValuesV.getRight();

        //TODO Use A*V / Sigma to find U
//        double maxEigenVal = DoubleStream.of(eigenValues)
////                .max()
////                .orElse(0);
//        Matrix u = a.multiply(v).scalarMultiply(1/maxEigenVal);

        Matrix aaT = a.multiply(aT);
        Pair<double[], Matrix> eigenValuesU = sortEigenValuesAndVectors(aaT.evd());
//        double[] eigenValues = eigenValuesU.getLeft();
        Matrix u = eigenValuesU.getRight();

        Matrix eigen = buildEigenMatrix(eigenValues, a.rowDimensions(), a.colDimensions());
        u = u.toOrthoNormal();
        v = v.toOrthoNormal();

        return new Triplet<>(u, eigen, v);
    }

    private Matrix buildEigenMatrix(double[] eigenValues, int row, int col) {
        double m[][] = new double[row][col];
        for(int i = 0; i < row; i++) {
            for (int j = 0; j< col; j++) {
                if (i == j && i < eigenValues.length) {
                    m[i][i] = eigenValues[i];
                } else {
                    m[i][j] = 0;
                }
            }
        }

        return new Matrix(m);
    }

    private Pair<double[], Matrix> sortEigenValuesAndVectors(Pair<Matrix, Matrix> evd) {
        Matrix eigenValuesMat = evd.getLeft();
        Matrix eigenVectors = evd.getRight();

        double eigenValues[] = new double[eigenValuesMat.colDimensions()];
        for(int i = 0; i < eigenValuesMat.colDimensions(); i++) {
            eigenValues[i] = Math.sqrt(eigenValuesMat.getElement(i, i));
        }


        for (int i = 0; i < eigenValues.length; i++) {
            double maxEigenValue = eigenValues[i];
            for (int j = i + 1; j < eigenValues.length; j++) {
                double val = eigenValues[j];
                if (val > maxEigenValue) {
                    eigenValues[j] = maxEigenValue;
                    eigenValues[i] = val;
                    eigenVectors =  eigenVectors.swapColumns(j, i);
                    maxEigenValue = val;
                }
            }
        }

        return new Pair<>(eigenValues, eigenVectors);
    }
}
