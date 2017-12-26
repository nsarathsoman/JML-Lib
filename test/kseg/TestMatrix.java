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

        double symetricMatrix[][] = {
                {1, 2, 3},
                {2, 5, 6},
                {3, 6, 9}
        };

        System.out.println("------IsSymmetricMatrix------");
        matrix = new Matrix(symetricMatrix);
        System.out.println(matrix.isSymmetric());

        System.out.println("------LargestOffDiagonalElement------");
        matrix = new Matrix(symetricMatrix);
        System.out.println(matrix.largestOffDiagonalElement());

        System.out.println("------Rotation matrix 2x2------");
        matrix = Matrix.buildRotationMatrix(20, 2, 2);
        matrix.dumpToConsole();

        System.out.println("------Rotation matrix 3x3------");
        matrix = Matrix.buildRotationMatrix(20, 3, 3);
        matrix.dumpToConsole();

        System.out.println("------Rotation matrix 4x4------");
        matrix = Matrix.buildRotationMatrix(20, 4, 4);
        matrix.dumpToConsole();


        System.out.println("------Transpose------");
        matrix = new Matrix(elements).transpose();
        matrix.dumpToConsole();

        System.out.println("------Transpose- Symmetrix------");
        matrix = new Matrix(symetricMatrix).transpose();
        matrix.dumpToConsole();

        System.out.println("------Scalar Multiply------");
        matrix = new Matrix(symetricMatrix).scalarMultiply(0.5);
        matrix.dumpToConsole();

        System.out.println("------Swap Column------");
        matrix = new Matrix(symetricMatrix).swapColumns(0, 1);
        matrix.dumpToConsole();

        System.out.println("------OrthoNormal------");
        double orthogonal[][] = new double[][]{
            {3, 4},
            {4, 3}
        };
        matrix = new Matrix(orthogonal).toOrthoNormal();
        matrix.dumpToConsole();

    }
}
