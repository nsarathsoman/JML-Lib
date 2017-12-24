package kseg.util;

public class Triplet<X1, X2, X3> {

    private X1 x1;
    private X2 x2;
    private X3 x3;

    public Triplet(X1 x1, X2 x2, X3 x3) {
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
    }

    public X1 getX1() {
        return x1;
    }

    public X2 getX2() {
        return x2;
    }

    public X3 getX3() {
        return x3;
    }

    @Override
    public String toString() {
        return "Triplet{" +
                "x1=" + x1 +
                ", x2=" + x2 +
                ", x3=" + x3 +
                '}';
    }
}
