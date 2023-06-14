package com.rolex.cgroup;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Slf4j
public class SystemOperation {
    public static boolean isRoot() throws IOException {
        String result = SystemOperation.exec("echo $EUID").substring(0, 1);
        return Integer.valueOf(result.substring(0, result.length())) == 0;
    }

    /**
     * 例如：mount -t cgroup -o cpu,cpuset,memory cpu_and_mem /cgroup/cpu_and_mem
     *
     * @param name
     * @param target
     * @param type
     * @param data
     * @throws IOException
     */
    public static void mount(String traceId, String name, String target, String type, String data) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("mount -t ").append(type).append(" -o ").append(data).append(" ")
                .append(name).append(" ").append(target);
        log.debug("{} mount command: {}", traceId, sb.toString());
        SystemOperation.exec(sb.toString());
    }

    public static void umount(String name) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("umount ").append(name);
        SystemOperation.exec(sb.toString());
    }

    public static String exec(String cmd) throws IOException {
        List<String> commands = new ArrayList<>();
        commands.add("/bin/bash");
        commands.add("-c");
        commands.add(cmd);
        return ProcessUtil.launchProcess(cmd, commands, new HashMap<String, String>(), false);
    }
}
