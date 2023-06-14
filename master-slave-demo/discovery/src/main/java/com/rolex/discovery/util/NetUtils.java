package com.rolex.discovery.util;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Slf4j
public class NetUtils {

    public static String NETWORK_INTERFACE_NAME;

    static {
        NETWORK_INTERFACE_NAME = System.getProperty("network.interface.name");
    }

    public static InetAddress getLocalHost() throws UnknownHostException {
        return InetAddress.getLocalHost();
    }

    public static String getLocalIP() throws Exception {
        return getLocalHost().getHostAddress();
    }

    public static String getHostname() {
        try {
            return getLocalHost().getHostName();
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) throws SocketException, UnknownHostException {
        System.out.println(getSiteIP());
        System.out.println(getHostName());
    }

    public static String getSiteIP() throws SocketException, UnknownHostException {
        return getSiteLocalAddress().getHostAddress();
    }

    public static String getHostName() throws SocketException, UnknownHostException {
        return getSiteLocalAddress().getHostName();
    }

    public static InetAddress getSiteLocalAddress() throws UnknownHostException, SocketException {
        InetAddress candidateAddress = null;
        Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
        while (netInterfaces.hasMoreElements()) {
            NetworkInterface ni = netInterfaces.nextElement();
            Enumeration<InetAddress> ias = ni.getInetAddresses();
            log.debug("find network: name={}, displayName={}", ni.getName(), ni.getDisplayName());
            if (ni.isVirtual() || ni.isLoopback() || ni.getName().startsWith("docker")) {
                log.debug("this network is invalid");
                continue;
            }
            while (ias.hasMoreElements()) {
                InetAddress inetAddress = ias.nextElement();
                String ip = inetAddress.getHostAddress();
                // 如果是ipv6或lo则跳过
                if (ip.indexOf(":") != -1) {
                    log.debug("this inetAddress is ipv6");
                    continue;
                }
                if (inetAddress.isLoopbackAddress()) {
                    log.debug("this inetAddress is lo");
                    continue;
                }
                if (inetAddress.isMulticastAddress()) {
                    log.debug("this inetAddress is multicast");
                    continue;
                }

                if (inetAddress.isSiteLocalAddress()) {
                    log.debug("SiteLocalAddress={}", inetAddress.getHostAddress());
                    return inetAddress;
                }
                log.debug("candidateAddress is {}", candidateAddress);
                if (candidateAddress == null) {
                    candidateAddress = inetAddress;
                }
            }
        }
        return candidateAddress == null ? InetAddress.getLocalHost() : candidateAddress;
    }

    /**
     * 获取地址字符串
     *
     * @param address 地址
     * @return 地址字符串
     */
    public static String getAddress(SocketAddress address) {
        if (address == null) {
            return null;
        }
        if (address instanceof InetSocketAddress) {
            InetSocketAddress isa = (InetSocketAddress) address;
            return isa.getAddress().getHostAddress() + ":" + isa.getPort();
        } else {
            return address.toString();
        }
    }


    /**
     * 地址转化成字节数组
     *
     * @param socketAddress 地址对象
     * @return 字节数组
     */
    public static byte[] toByte(InetSocketAddress socketAddress) {
        if (socketAddress == null) {
            throw new IllegalArgumentException("socketAddress is null");
        }
        InetAddress inetAddress = socketAddress.getAddress();
        if (inetAddress == null) {
            throw new IllegalArgumentException("socketAddress is invalid");
        }
        byte[] address = inetAddress.getAddress();
        byte[] result = new byte[address.length + 2];
        System.arraycopy(address, 0, result, 2, address.length);
        int port = socketAddress.getPort();
        result[1] = (byte) (port >> 8 & 0xFF);
        result[0] = (byte) (port & 0xFF);
        return result;
    }


    /**
     * 把10进制地址字符串转化成字节数组
     *
     * @param address 地址
     * @return 字节数组
     */
    public static byte[] toByteByAddress(String address) {
        if (address == null) {
            throw new IllegalArgumentException("address is invalid");
        }
        String[] ips = address.split("[.:_]");
        if (ips.length < 4) {
            throw new IllegalArgumentException("broker is invalid");
        }
        int pos = 0;
        byte[] buf;
        if (ips.length > 4) {
            buf = new byte[6];
            int port = Integer.parseInt(ips[4]);
            buf[pos++] = (byte) (port & 0xFF);
            buf[pos++] = (byte) (port >> 8 & 0xFF);
        } else {
            buf = new byte[4];
        }
        buf[pos++] = (byte) Integer.parseInt(ips[0]);
        buf[pos++] = (byte) Integer.parseInt(ips[1]);
        buf[pos++] = (byte) Integer.parseInt(ips[2]);
        buf[pos++] = (byte) Integer.parseInt(ips[3]);
        return buf;
    }
}
