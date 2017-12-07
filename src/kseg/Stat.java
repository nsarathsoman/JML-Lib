package kseg;

import kseg.util.Pair;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class Stat {

    //Measures of central tendencies
    public static <T extends Number> double mean(List<T> vector) {
        return Vectors.mean(vector);
    }

    public static <T extends Number> double median(List<T> vector) {
        int size = vector.size();

        List<T> sortedVector = vector.stream()
                .sorted()
                .collect(Collectors.toList());
        int midPt = size / 2;
        if(size % 2 == 1) {
            return sortedVector.get(midPt)
                    .doubleValue();
        } else {
            int loMidPt = midPt - 1;
            int highMidPt = midPt;
            return (sortedVector.get(loMidPt).doubleValue() + sortedVector.get(highMidPt).doubleValue()) / 2;
        }
    }

    public static <T extends Number> T mode(List<T> vector) {
        Optional<Map.Entry<T, Long>> modeEntry = vector.stream()
                .map(element -> new Pair<>(element, 1l))
                .collect(groupingBy(Pair::getLeft, summingLong(Pair::getRight)))
                .entrySet()
                .stream()
                .collect(maxBy(Comparator.comparingLong(element-> element.getValue())));

        if(modeEntry.isPresent()) {
            return modeEntry.get()
                    .getKey();
        } else {
            return null;
        }

    }

    public static <T extends Number> T quantile(List<T> vector, float percentage) {
        int size = vector.size();
        int percIndex = (int) (size * percentage);
        if(size <= percIndex) {
            throw new RuntimeException("Percentage should be less than 1");
        }
        return vector.stream()
                .sorted()
                .collect(Collectors.toList())
                .get(percIndex);
    }

}
