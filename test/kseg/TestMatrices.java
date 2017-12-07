package kseg;

import kseg.util.Pair;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class TestMatrices {

    @Test
    public void testMatrices() {
        List<List<Integer>> matrix = Arrays.asList(Arrays.asList(1, 2, 3)
                , Arrays.asList(4, 5, 6)
                , Arrays.asList(7, 8, 9));

        System.out.println("------Add------");
        Pair<Integer, Integer> rowCol = Matrices.shape(matrix);
        System.out.println(rowCol);
        System.out.println();

        System.out.println("------GetRow------");
        List<Integer> row = Matrices.getRow(matrix, 2);
        System.out.println(row);
        System.out.println();

        try {
            System.out.println("------GetRowErr------");
            Matrices.getRow(matrix, 3);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.out.println();
        }

        System.out.println("------GetColumn------");
        List<Integer> column = Matrices.getColumn(matrix, 1);
        System.out.println(column);
        System.out.println();

        try {
            System.out.println("------GetColumnErr------");
            Matrices.getColumn(matrix, 3);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.out.println();
        }

        System.out.println("------MakeDiagonalMatrix------");
        List<List<Integer>> diagonalMatrix = Matrices.makeMatrix(3, 3, (i, j) -> i == j ? 1 : 0);
        System.out.println(diagonalMatrix);
        System.out.println();
    }
}
