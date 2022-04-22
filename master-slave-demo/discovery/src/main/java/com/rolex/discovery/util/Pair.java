package com.rolex.discovery.util;

/**
 * key value pair
 *
 * @param <L> L generic type
 * @param <R> R generic type
 */
public class Pair<L, R> {

    private L _1;

    private R _2;

    public Pair(L left, R right) {
        this._1 = left;
        this._2 = right;
    }

    public L _1() {
        return _1;
    }

    public void _1(L _1) {
        this._1 = _1;
    }

    public R _2() {
        return _2;
    }

    public void _2(R _2) {
        this._2 = _2;
    }

    public static <L, R> Pair<L, R> of(L left, R right) {
        return new Pair<>(left, right);
    }
}