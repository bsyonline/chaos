/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.slidingwindow;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

/**
 * @author rolex
 * @since 2020
 */
public class SlidingWindowExample {

    public static void main(String[] args) {
        SlidingWindowSpliterator.window(Arrays.asList(1,2,3,4,5), 3)
                .map(group -> group.collect(toList()))
                .forEach(System.out::println);
    }

    static class SlidingWindowSpliterator<T> implements Spliterator<Stream<T>> {
        private Queue queue;
        private Iterator<T> iterator;
        private int windowSize; // 窗口大小
        private int size;

        public SlidingWindowSpliterator(Collection<T> collection, int windowSize) {
            queue = new ArrayDeque(windowSize);
            iterator = collection.iterator();
            this.windowSize = windowSize;
            size = calSize(collection);
        }

        static <T> Stream<Stream<T>> window(Collection<T> collection, int windowSize){
           return StreamSupport.stream(new SlidingWindowSpliterator<>(collection, windowSize), false);
        }

        @Override
        public boolean tryAdvance(Consumer<? super Stream<T>> action) {
            if(windowSize<1) {
                return false;
            }
            while(iterator.hasNext()){
                queue.add(iterator.next());
                if(queue.size() == windowSize){
                    action.accept(Arrays.stream((T[])queue.toArray(new Object[0])));
                    queue.poll();
                    return iterator.hasNext();
                }
            }
            return false;
        }

        @Override
        public Spliterator<Stream<T>> trySplit() {
            return null;
        }

        @Override
        public long estimateSize() {
            return size;
        }

        @Override
        public int characteristics() {
            return ORDERED | NONNULL | SIZED;
        }

        private int calSize(Collection<T> collection) {
            return collection.size() < windowSize ? 0 : collection.size() - windowSize + 1;

        }
    }

}
