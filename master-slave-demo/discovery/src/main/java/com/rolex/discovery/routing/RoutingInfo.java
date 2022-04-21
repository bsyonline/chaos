package com.rolex.discovery.routing;

import com.rolex.discovery.observer.RoutingInfoObserver;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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

    public RoutingInfo(int nodeId, String ip, int port, NodeType type, long timestamp, String connectedServer) {
        this.nodeId = nodeId;
        this.ip = ip;
        this.port = port;
        this.type = type;
        this.timestamp = timestamp;
        this.connectedServer = connectedServer;
        this.addObserver(new RoutingInfoObserver());
    }

    int nodeId;
    String ip;
    int port;
    NodeType type;
    long timestamp;
    String connectedServer;

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
