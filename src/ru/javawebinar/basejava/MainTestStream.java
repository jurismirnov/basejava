package ru.javawebinar.basejava;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainTestStream {

    private static int minValue(int[] values) {
        List<Integer> input = Arrays.stream(values)
                .distinct()
                .boxed()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        return (int) IntStream.range(0, input.size()).mapToDouble(i -> input.get(i) * Math.pow(10, i)).sum();
    }

    private static int minValue2(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted().reduce((x, y) -> x * 10 + y).getAsInt();
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        boolean even = integers.stream().reduce(Integer::sum).orElse(0) % 2 != 0;
        return integers.stream().filter(even ? x -> x % 2 != 0 : x -> x % 2 == 0).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        System.out.println(minValue(new int[]{1, 4, 1, 2, 2, 4}));
        System.out.println(minValue(new int[]{1, 4, 1, 3, 2, 4, 8}));

        System.out.println(minValue2(new int[]{1, 4, 1, 2, 2, 4}));
        System.out.println(minValue2(new int[]{1, 4, 1, 3, 2, 4, 8}));

        System.out.println(oddOrEven(Arrays.asList(1, 4, 1, 2, 2, 4)));
        System.out.println(oddOrEven(Arrays.asList(1, 4, 3, 1, 2, 2, 4)));
    }

}
