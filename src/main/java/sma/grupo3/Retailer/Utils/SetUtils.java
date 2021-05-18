package sma.grupo3.Retailer.Utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SetUtils {
    public static List<String> sort(Stream<String> set) {
        return set.sorted((o1, o2) -> {
            int numericIndex1 = o1.lastIndexOf("_");
            int numericIndex2 = o2.lastIndexOf("_");
            if (o1.substring(0, numericIndex1).equals(o2.substring(0, numericIndex2))) {
                int a = Integer.parseInt(o1.substring(numericIndex1 + 1, o1.length()));
                int b = Integer.parseInt(o2.substring(numericIndex2 + 1, o2.length()));
                return Integer.compare(a, b);
            } else {
                return o1.compareTo(o2);
            }
        }).collect(Collectors.toList());
    }
}
