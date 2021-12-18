
package com.rolex.alphax.util;

import lombok.extern.slf4j.Slf4j;

import java.net.Inet4Address;
import java.net.UnknownHostException;


@Slf4j
public final class NetUtil {
    public static String getLocalHost() {
        try {
            return Inet4Address.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getLocalIp(){
        try {
            return Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "";
    }
    private NetUtil(){}

    public static void main(String[] args) {
        System.out.println(getLocalHost());
        System.out.println(getLocalIp());
    }
}
