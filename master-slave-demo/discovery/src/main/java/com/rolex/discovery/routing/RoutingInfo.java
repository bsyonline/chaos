package com.rolex.discovery.routing;

import com.alibaba.fastjson.JSONObject;
import com.rolex.discovery.observer.Observer;
import com.rolex.discovery.observer.RoutingInfoObserver;
import lombok.Data;
import org.springframework.context.ApplicationEventPublisher;

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
public class RoutingInfo {
    List<RoutingInfoObserver> observers = new ArrayList<>();

    public RoutingInfo() {
    }

    public RoutingInfo(Host host, NodeType type, long timestamp, Set<Host> connected, Metrics metrics) {
        this.host = host;
        this.type = type;
        this.timestamp = timestamp;
        this.connected.addAll(connected);
        this.metrics = metrics;
        this.addObserver(new RoutingInfoObserver());
    }

    Host host;
    NodeType type;
    long timestamp;
    Set<Host> connected = new HashSet<>();
    Metrics metrics;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        Host host;
        NodeType type;
        Set<Host> connected = new HashSet<>();
        Metrics metrics;
        List<RoutingInfoObserver> observers;

        public RoutingInfo.Builder host(Host host) {
            this.host = host;
            return this;
        }

        public RoutingInfo.Builder type(NodeType type) {
            this.type = type;
            return this;
        }

        public RoutingInfo.Builder connected(Set<Host> connected) {
            this.connected = connected;
            return this;
        }

        public RoutingInfo.Builder metrics(Metrics metrics) {
            this.metrics = metrics;
            return this;
        }

        public RoutingInfo build() {
            return new RoutingInfo(this.host, this.type, System.currentTimeMillis(), this.connected, this.metrics);
        }
    }

    public void addObserver(RoutingInfoObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(RoutingInfoObserver observer) {
        observers.remove(observer);
    }

    public void routingChange(ApplicationEventPublisher applicationEventPublisher,RoutingInfo changed) {
        observers.forEach(o -> o.onChange(applicationEventPublisher, this, changed));
    }
}