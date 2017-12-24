package kseg;

public class TestMatrix {

    public static void main(String[] args) {
        double elements[][] = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        System.out.println("------Add------");
        Matrix matrix = new Matrix(elements).add(new Matrix(elements));
        matrix.dumpToConsole();

        System.out.println("------Substract------");
        matrix = new Matrix(elements).substract(new Matrix(elements));
        matrix.dumpToConsole();

        System.out.println("------Substract------");
        matrix = new Matrix(elements).substract(new Matrix(elements));
        matrix.dumpToConsole();

        double X[][] = {
                {1, 2, 3},
                {4, 5, 6}
        };
        double Y[][] = {
                {7, 8},
                {9, 10},
                {11, 12}
        };

        System.out.println("------Multiply------");
        //output expected
//        58.0  |  64.0  |
//        139.0  |  154.0  |
        matrix = new Matrix(X).multiply(new Matrix(Y));
        matrix.dumpToConsole();
    }
}
