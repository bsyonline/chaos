package com.rolex.alphax.java8.stream;

import lombok.Data;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

@Data
public class StringCollector implements Collector<String, StringJoiner, String> {

    private String delimiter;
    private String prefix;
    private String suffix;
    private Set<Characteristics> characteristics;

    public StringCollector(String delimiter, String prefix, String suffix) {
        this.delimiter = delimiter;
        this.prefix = prefix;
        this.suffix = suffix;
        this.characteristics = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.UNORDERED,
                Collector.Characteristics.IDENTITY_FINISH));
    }

    /**
     * 第一步 先创建出新的容器
     * @return
     */
    @Override
    public Supplier<StringJoiner> supplier() {
        return () -> new StringJoiner(delimiter, prefix, suffix);
    }

    /**
     * 第二步 结合之前操作的结果和当前值，生成并返回新的值。
     * @return
     */
    @Override
    public BiConsumer<StringJoiner, String> accumulator() {
        return StringJoiner::add;
    }

    /**
     * 第三步 如果有两个容器，需要将其合并
     * @return
     */
    @Override
    public BinaryOperator<StringJoiner> combiner() {
        return StringJoiner::merge;
    }

    /**
     * 第四步 将 toString 方法内联到方法链的末端
     * @return
     */
    @Override
    public Function<StringJoiner, String> finisher() {
        return StringJoiner::toString;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return characteristics;
    }
}
