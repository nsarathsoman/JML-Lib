package kseg;

import kseg.util.Pair;
import kseg.util.Triplet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Matrix {

    private double elements[][];
    private int r, c;

    public Matrix(double elements[][]) {
        this.elements = elements;
        this.r = elements.length;
        this.c = elements[0].length;
    }

    public double getElement(int i, int j) {
        return elements[i][j];
    }

    public int rowDimensions() {
        return r;
    }

    public int colDimensions() {
        return c;
    }

    public Matrix add(Matrix that) {
        if (r != that.rowDimensions() || c != that.colDimensions())
            throw new RuntimeException("Matrix size mismatch, cannot proceed with addition");

        double mat[][] = new double[r][c];

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                mat[i][j] = getElement(i, j) + that.getElement(i, j);
            }
        }

        return new Matrix(mat);
    }

    public Matrix substract(Matrix that) {
        if (r != that.rowDimensions() || c != that.colDimensions())
            throw new RuntimeException("Matrix size mismatch, cannot proceed with substraction");

        double mat[][] = new double[r][c];

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                mat[i][j] = getElement(i, j) - that.getElement(i, j);
            }
        }

        return new Matrix(mat);
    }

    public Matrix multiply(Matrix that) {
        if (c != that.rowDimensions())
            throw new RuntimeException("Matrix size mismatch this.c!=that.r, cannot proceed with multiplication");

        double result[][] = new double[r][that.colDimensions()];

        for (int i = 0; i < r; i++) {
            double row[] = getRow(i);
            for (int j = 0; j < that.colDimensions(); j++) {
                double thatCol[] = that.getColumn(j);
                result[i][j] = Vectors.dot(row, thatCol);
            }
        }

        return new Matrix(result);
    }

    public double[] getRow(int i) {
        double elem[] = new double[c];
        for (int j = 0; j < c; j++) {
            elem[j] = getElement(i, j);
        }

        return elem;
    }


    public double[] getColumn(int j) {
        double elem[] = new double[r];
        for (int i = 0; i < r; i++) {
            elem[i] = getElement(i, j);
        }

        return elem;
    }

    public boolean isSquareMatrix() {
        return r != c ? false : true;
    }

    public boolean isSymmetric() {
        if (!isSquareMatrix()) {
            return false;
        }

        boolean isSymmetric = true;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (i == j) continue;
                isSymmetric = isSymmetric && getElement(i, j) == getElement(j, i);
            }
        }

        return isSymmetric;
    }


    public Triplet<Double, Integer, Integer> largestOffDiagonalElement() {
        double largestElem = 0;
        int iIndex = 0;
        int jIndex = 1;
        if(1 >= c) {
            largestElem = getElement(1, 0);
            iIndex = 1;
            jIndex = 0;
        } else {
            largestElem = getElement(0, 1);
            iIndex = 0;
            jIndex = 1;
        }
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (i == j) continue;
                double currElem = getElement(i, j);
                if (largestElem < currElem) {
                    largestElem = currElem;
                    iIndex = i;
                    jIndex = j;
                }

            }
        }

        return new Triplet<>(largestElem, iIndex, jIndex);
    }

    public Matrix transpose() {

        double elem[][] = new double[c][r];

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                elem[j][i] = getElement(i, j);
            }
        }

        return new Matrix(elem);

    }

    public Pair<Matrix, Matrix> evd() {
        return new EVD().evd(this);
    }

    public Triplet<Matrix, Matrix, Matrix> svd() {
        return new SVD().svd(this);
    }

    public Matrix scalarMultiply(double scalar) {
        double m[][] = new double[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                m[i][j] = getElement(i, j) * scalar;
            }
        }

        return new Matrix(m);
    }

    public Matrix swapColumns(int maxIndex, int index) {
        double m[][] = new double[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if(j == maxIndex || j == index) continue;
                m[i][j] = getElement(i, j);
            }

            double tmp = getElement(i, maxIndex);
            m[i][maxIndex] = getElement(i, index);
            m[i][index] = tmp;
        }

        return new Matrix(m);
    }

    public Matrix toOrthoNormal() {
        //TODO add orthogonality check
        double m[][] = new double[r][c];
        for (int j = 0; j < c; j++) {
            double column[] = getColumn(j);
            double unitVector[] = Vectors.toUnitVector(column);
            for (int i = 0; i < r; i++) {
                m[i][j] = unitVector[i];
            }
        }

        return new Matrix(m);
    }

    public Matrix reshape(int row, int col) {
        if(row > r || col > c) throw new RuntimeException("Dimesions should match");
        double m[][] = new double[r][c];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                m[i][j] = getElement(i, j);
            }
        }

        return new Matrix(m);
    }

    public Matrix roundOff() {
        Matrix b = this;
        double epsilon = 0.0000001;

        double m[][] = new double[b.rowDimensions()][b.colDimensions()];
        for (int i = 0; i < b.rowDimensions(); i++) {
            for(int j = 0; j < b.colDimensions(); j ++) {
                double element = b.getElement(i, j);
                if(Math.abs(element) < epsilon) {
                    m[i][j] = 0;
                } else {
                    m[i][j] = element;
                }
            }
        }

        return new Matrix(m);
    }

    public void dumpToConsole() {
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                System.out.printf("  " + getElement(i, j) + "  |");
            }
            System.out.println();
        }
    }

    //Static Utilities
    public static Matrix buildRotationMatrix(double theta, int m, int n) {
        if (m != n) {
            throw new RuntimeException("Rotation matrix construction is allowed for square matrixes alone");
        }

        double elem[][] = new double[m][m];
        elem[0][0] = Math.cos(theta);
        elem[0][1] = -Math.sin(theta);
        elem[1][0] = Math.sin(theta);
        elem[1][1] = Math.cos(theta);

        if (m > 2) {
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < m; j++) {
                    if ((i == 0 || i == 1) && j <= 1) continue;
                    if (i == j) {
                        elem[i][j] = 1;
                    } else {
                        elem[i][j] = 0;
                    }
                }
            }
        }

        return new Matrix(elem);
    }

    public double[] getColumn(int[] ovLapItemIndices, int j) {
        List<Integer> itemIndices = IntStream.of(ovLapItemIndices)
                .boxed()
                .collect(Collectors.toList());
        double el[] = new double[ovLapItemIndices.length];
        int elIndex = 0;
        for (int i = 0; i < r; i++) {
            if(itemIndices.contains(i)) {
                el[elIndex++] = getElement(i, j);
            }
        }

        return el;
    }
}
