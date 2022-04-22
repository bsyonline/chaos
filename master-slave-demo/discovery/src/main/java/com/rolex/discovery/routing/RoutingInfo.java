package com.rolex.discovery.routing;

import com.rolex.discovery.observer.RoutingInfoObserver;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Data
@AllArgsConstructor
public class RoutingInfo {
    List<RoutingInfoObserver> observers = new ArrayList<>();

    public RoutingInfo(Host host, NodeType type, long timestamp, Set<Host> connected) {
        this.host = host;
        this.type = type;
        this.timestamp = timestamp;
        this.connected.addAll(connected);
        this.addObserver(new RoutingInfoObserver());
    }

    Host host;
    NodeType type;
    long timestamp;
    Set<Host> connected = new HashSet<>();

    public void addObserver(RoutingInfoObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(RoutingInfoObserver observer) {
        observers.remove(observer);
    }

    public void routingChange() {
        observers.forEach(o -> o.onChange(this));
    }
}
