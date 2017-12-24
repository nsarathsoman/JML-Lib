package kseg;

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
}
