package kseg;

import kseg.util.Pair;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Matrices {

    public static <T> Pair<Integer, Integer> shape(List<List<T>> matrix) {
        int noOfRows = matrix.size();
        int noOfColumns = matrix.size() > 0 ? matrix.get(0).size() : 0;
        return new Pair(noOfRows, noOfColumns);
    }

    public static <T> List<T> getRow(List<List<T>> matrix, int i) {
        Pair<Integer, Integer> shape = shape(matrix);
        if(shape.getLeft() <= i) {
            throw new RuntimeException(i + " is out of range");
        }
        return matrix.get(i);
    }

    public static <T> List<T> getColumn(List<List<T>> matrix, int j) {
        Pair<Integer, Integer> shape = shape(matrix);

        if(shape.getRight() <= j) {
            throw new RuntimeException(j + " is out of range");
        }

        List<T> columnElements = matrix.stream()
                .map(row -> getColumnElement(row, j))
                .collect(Collectors.toList());
        if(!shape.getRight().equals(columnElements.size())) {
            throw new RuntimeException("fetched column size doesn't match with the matrix shape");
        }

        return columnElements;
    }

    private static <T> T getColumnElement(List<T> row, int j) {
        return IntStream.range(0, row.size())
                .filter(i -> i == j)
                .mapToObj(i -> row.get(i))
                .findFirst()
                .orElse(null);
    }

    public static <T> List<List<T>> makeMatrix(int noOfRows, int noOfColumns, MatrixElementSupplier<T> supplier) {
        return IntStream.range(0, noOfRows)
                .mapToObj(i -> makeRow(noOfColumns, i, supplier))
                .collect(Collectors.toList());
    }

    private static <T> List<T> makeRow(int noOfColoumns, int rowIndex, MatrixElementSupplier<T> supplier) {
        return IntStream.range(0, noOfColoumns)
                .mapToObj(j -> supplier.getElement(rowIndex, j))
                .collect(Collectors.toList());
    }

    @FunctionalInterface
    public interface MatrixElementSupplier<T> {
        T getElement(int i, int j);
    }
}
