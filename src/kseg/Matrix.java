package kseg;

import kseg.util.Pair;
import kseg.util.Triplet;

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

    public int getNoOfRows() {
        return r;
    }

    public int getNoOfColumns() {
        return c;
    }

    public Matrix add(Matrix that) {
        if (r != that.getNoOfRows() || c != that.getNoOfColumns())
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
        if (r != that.getNoOfRows() || c != that.getNoOfColumns())
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
        if (c != that.getNoOfRows())
            throw new RuntimeException("Matrix size mismatch this.c!=that.r, cannot proceed with multiplication");

        double result[][] = new double[r][that.getNoOfColumns()];

        for (int i = 0; i < r; i++) {
            double row[] = getRow(i);
            for (int j = 0; j < that.getNoOfColumns(); j++) {
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
        double largestElem = getElement(0, 1);
        int iIndex = 0;
        int jIndex = 1;
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
        return new EigenValueDecomposition().evd(this);
    }

    public Triplet<Matrix, Matrix, Matrix> svd() {
        return new SingularValueDecomposition().svd(this);
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

}
