package kseg.util;

import java.util.List;

public class Utils {


    public static void peek(List<Double> vector) {
        vector.stream()
                .peek(xi -> System.out.println(xi))
                .count();
        System.out.println();
    }
}
