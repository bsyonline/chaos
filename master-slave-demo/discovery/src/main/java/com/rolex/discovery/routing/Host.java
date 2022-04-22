package com.rolex.discovery.routing;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Data
public class Host implements Serializable {
    String host;
    int port;

    private Host() {
    }

    private Host(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Host host1 = (Host) o;
        return port == host1.port && host.equals(host1.host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, port);
    }

    public static Host of(String host, int port) {
        return new Host(host, port);
    }

    @Override
    public String toString() {
        return host + ":" + port;
    }
}
