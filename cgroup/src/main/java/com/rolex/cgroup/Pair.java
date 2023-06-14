package com.rolex.cgroup;

import java.io.Serializable;
import java.util.Objects;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class Pair<F, S> implements Serializable {
    private static final long serialVersionUID = -7149606757796890787L;
    private F first;
    private S second;

    public Pair() {
    }

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public F getFirst() {
        return first;
    }

    public void setFirst(F first) {
        this.first = first;
    }

    public S getSecond() {
        return second;
    }

    public void setSecond(S second) {
        this.second = second;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Pair)) {
            return false;
        }
        Pair<?, ?> otherPair = (Pair<?, ?>) other;
        // Arrays are very common as values and keys, so deepEquals is mandatory
        return Objects.deepEquals(this.first, otherPair.first)
                && Objects.deepEquals(this.second, otherPair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("first:" + first);
        sb.append(":");
        sb.append("second:" + second);
        return sb.toString();
    }
}
