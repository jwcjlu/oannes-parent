package com.jwcjlu.oannes.transport;

public class NettyConfiguration {
    public int getConnectionTimeoutMs() {
        return 2000;
    }

    public boolean isUseTcpNoDelay() {
        return false;
    }

    public int maxConnectionsPerHosts() {
        return 0;
    }
}
