package kseg;

@FunctionalInterface
public interface MatrixElementSupplier<T> {
    T getElement(int i, int j);
}
