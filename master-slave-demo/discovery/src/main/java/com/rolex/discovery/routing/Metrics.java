package com.rolex.discovery.routing;

import lombok.Data;

import java.io.Serializable;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Data
public class Metrics implements Serializable {
    private static final long serialVersionUID = -4498278210481188718L;
    double cup;
    double memory;

    private Metrics(double cup, double memory) {
        this.cup = cup;
        this.memory = memory;
    }

    private Metrics() {
    }

    public static Metrics of(double cpu, double memory){
        return new Metrics(cpu, memory);
    }
}
