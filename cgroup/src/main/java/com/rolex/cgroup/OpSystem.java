package com.rolex.cgroup;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class OpSystem {
    private static String OS = System.getProperty("os.name").toLowerCase();

    public static boolean isLinux() {
        return OS.contains("linux");
    }

    public static boolean isMac() {
        return OS.contains("mac") && OS.indexOf("os") > 0;
    }
}
