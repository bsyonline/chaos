/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.java8.stream;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author rolex
 * @since 2020
 */
public class StreamExample {
    public static void main(String[] args) {
        // collect
        List<Integer> collect1 = Stream.of(1, 2, 3).collect(Collectors.toList());
        System.out.println("collect1=" + collect1);
        // map
        List<String> collect2 = Stream.of("a", "b", "c").map(s -> s.toUpperCase()).collect(Collectors.toList());
        System.out.println("collect2=" + collect2);
        // filter
        List<Integer> collect3 = Stream.of(1, 2, 3).filter(s -> s > 2).collect(Collectors.toList());
        System.out.println("collect3=" + collect3);
        // flatMap
        List<String> collect4 = Stream.of(Stream.of("a", "b", "c").collect(Collectors.toList()), Stream.of("d", "e", "f").collect(Collectors.toList()), Stream.of("g", "h", "i").collect(Collectors.toList()))
                .flatMap(c -> c.stream().map(s -> s.toUpperCase())).collect(Collectors.toList());
        System.out.println("collect4=" + collect4);
        // max/min
        Integer v1 = Stream.of(1, 2, 3).max(Comparator.comparing(c -> c)).get();
        System.out.println("v1=" + v1);
        // reduce
        Integer v2 = Stream.of(1, 2, 3).reduce(0, (x, e) -> x + e);
        System.out.println("v2=" + v2);
        // maxBy/minBy
        Integer v3 = Stream.of(1, 2, 3).collect(Collectors.maxBy(Comparator.comparing(i -> i))).get();
        System.out.println("v3=" + v3);
        // partitioningBy
        Map<Boolean, List<Integer>> collect5 = Stream.of(1, 2, 3).collect(Collectors.partitioningBy(i -> i % 2 == 0));
        System.out.println("collect5=" + collect5);
        // joining
        String v4 = Stream.of("a", "b", "c").collect(Collectors.joining(",", "", ""));
        System.out.println("v4=" + v4);
        // groupingBy+counting
        Map<Integer, Long> collect6 = Stream.of(1, 2, 3, 2, 3, 3).collect(Collectors.groupingBy(i -> i, Collectors.counting()));
        System.out.println("collect6=" + collect6);
        // groupingBy+mapping
        Map<Integer, List<Integer>> collect7 = Stream.of(1, 2, 3, 2, 2, 3).collect(Collectors.groupingBy(i -> i, Collectors.mapping(i -> i, Collectors.toList())));
        System.out.println("collect7=" + collect7);
        // custom collector, like joining
//        String v5 = Stream.of("a", "b", "c").collect(new StringCollector(", ", "[", "]"));
//        System.out.println("v5=" + v5);
        // word count
        Map<String, Long> collect8 = Stream.of("John", "Paul", "George", "John", "Paul", "John")
                .map(s -> s.chars().mapToObj(c -> Character.toString((char) c)).collect(Collectors.toList()))
                .flatMap(l -> l.stream())
                .collect(Collectors.groupingBy(str -> str, Collectors.counting()));
        System.out.println("collect8=" + collect8);


        int[] arr = new int[]{1,2,3,4};
        Arrays.parallelPrefix(arr, Integer::sum);
        System.out.println(Arrays.stream(arr).mapToObj(d->String.valueOf(d)).collect(Collectors.joining(",","","")));

        Stream.of(1,2,3,4).mapToLong(l->l);



        double[] doubles = simpleMovingAverage(new double[]{0, 1, 2, 3, 4, 3.5}, 3);
        System.out.println(Arrays.stream(doubles).mapToObj(d->String.valueOf(d)).collect(Collectors.joining(",","","")));

        /*
            map to list
         */
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "A");
        map.put(2, "B");
        map.put(3, "C");
        List<Integer> collect9 = map.entrySet().stream().map(kv -> kv.getKey()).collect(Collectors.toList());
        System.out.println(collect9);

        /*
            list to map
         */
        Map<Integer, Integer> collect10 = collect9.stream().collect(Collectors.toMap(k -> k, k -> k));
        System.out.println(collect10);

        /*
            to TreeSet
         */
        TreeSet<Integer> collect11 = collect9.stream().collect(Collectors.toCollection(TreeSet::new));
        System.out.println(collect11);

    }

    public static double[] simpleMovingAverage(double[] values, int n) {
        double[] sums = Arrays.copyOf(values, values.length); // 不修改原数组
        Arrays.parallelPrefix(sums, Double::sum); // 并行计算，将数组的元素相加。
        int start = n - 1;
        return IntStream.range(start, sums.length) // 得到包含所需元素下标的流
                .mapToDouble(i -> {
                    double prefix = i == start ? 0 : sums[i - n];
                    return (sums[i] - prefix) / n; // 求平均
                }).toArray(); //
    }
}
