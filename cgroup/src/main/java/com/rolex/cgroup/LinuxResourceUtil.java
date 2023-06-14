package com.rolex.cgroup;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Slf4j
public class LinuxResourceUtil {
    private static final Pattern CPU_TIME_FORMAT_ALL =
            Pattern.compile("^cpu[ \t]+([0-9]+)[ \t]+([0-9]+)[ \t]+([0-9]+)[ \t]+([0-9]+)[ \t]+([0-9]+)[ \t]+([0-9]+)[ \t]+([0-9]+)");

    private static final Pattern CPU_TIME_FORMAT = Pattern.compile("^cpu[ \t]+([0-9]+)[ \t]+([0-9]+)[ \t]+([0-9]+)");

    private static final String PROCFS_STAT = "/proc/stat";
    private static final String PROCFS_MEMINFO = "/proc/meminfo";
    private static final String PROCFS_NETSTAT = "/proc/net/dev";

    private static volatile String duHome = "./";

    private static long lastCpuTime = -1;
    private static long lastIdieTime = -1;
    private static float lastcpuUsage = -1;

    public static int getProcessNum() {
        int sysCpuNum = 0;
        try {
            sysCpuNum = Runtime.getRuntime().availableProcessors();
        } catch (Exception e) {
            log.info("Failed to get CPU cores .");
        }
        return sysCpuNum;
    }

    public static synchronized float getTotalCpuUsage() {
        if (!OpSystem.isLinux()) {
            return 0.0f;
        }
        ProcResourceParse procResourceParse = new ProcResourceParse(PROCFS_STAT, new ResourceCallback() {
            @Override
            public Object execute(List<String> lines) throws Exception {
                Pair<Long, Long> totalCpu2idle = null;
                for (String line : lines) {
                    long totalCpuTime = 0;
                    long idleCpuTime = 0;
                    int size = 0;
                    Matcher matcher = CPU_TIME_FORMAT_ALL.matcher(line);
                    if (matcher.find()) {
                        size = 8;
                    } else {
                        matcher = CPU_TIME_FORMAT.matcher(line);
                        if (matcher.find()) {
                            size = 5;
                        }
                    }
                    for (int i = 1; i < size; i++) {
                        long value = ParseUtil.parseLong(matcher.group(i));
                        totalCpuTime += value;
                        if (i == 4) {
                            idleCpuTime = value;
                        }
                    }
                    if (size > 0) {
                        totalCpu2idle = new Pair<>();
                        totalCpu2idle.setFirst(totalCpuTime);
                        totalCpu2idle.setSecond(idleCpuTime);
                        break;
                    }
                }
                return totalCpu2idle;
            }
        });
        Object result = procResourceParse.getResource();
        if (result == null) {
            log.warn("getTotalCpuUsage failed");
            return 0.0f;
        }

        Pair<Long, Long> totalCpu2idle = (Pair<Long, Long>) result;
        if (lastCpuTime == -1 ||
                lastCpuTime > totalCpu2idle.getFirst()) {
            lastCpuTime = totalCpu2idle.getFirst();
            lastIdieTime = totalCpu2idle.getSecond();
            return lastcpuUsage;//return -1 first time
        }

        if (totalCpu2idle.getFirst() > lastCpuTime + 1) {
            float deltaCpu = totalCpu2idle.getFirst() - lastCpuTime;
            float deltaIdle = totalCpu2idle.getSecond() - lastIdieTime;
            lastcpuUsage = (1 - deltaIdle / deltaCpu) * 100f;
            lastCpuTime = totalCpu2idle.getFirst();
            lastIdieTime = totalCpu2idle.getSecond();
        }
        return lastcpuUsage;
    }

    public static double getTotalMemUsage() {
        if (!OpSystem.isLinux()) {
            return 0.0;
        }
        try {
            Map<String, String> memInfo = new HashMap<>();
            List<String> lines = IOUtils.readLines(new FileInputStream(PROCFS_MEMINFO));
            for (String line : lines) {
                String key = line.split("\\s+")[0];
                String value = line.split("\\s+")[1];
                memInfo.put(key, value);
            }
            String total = memInfo.get("MemTotal:");
            String free = memInfo.get("MemFree:");
            String buffer = memInfo.get("Buffers:");
            String cache = memInfo.get("Cached:");
            return 1 - (Double.valueOf(free) + Double.valueOf(buffer) + Double.valueOf(cache)) / Double.valueOf(total);
        } catch (Exception ignored) {
            log.warn("failed to get total memory usage.", ignored);
        }
        return 0.0;
    }

    /**
     * 总内存大小
     *
     * @return bytes
     */
    public static long getTotalMem() {
        if (!OpSystem.isLinux()) {
            return -1;
        }
        try {
            Map<String, String> memInfo = new HashMap<>();
            List<String> lines = IOUtils.readLines(new FileInputStream(PROCFS_MEMINFO));
            for (String line : lines) {
                String key = line.split("\\s+")[0];
                String value = line.split("\\s+")[1];
                memInfo.put(key, value);
            }
            String total = memInfo.get("MemTotal:");
            return Long.valueOf(total) * 1024;
        } catch (Exception ignored) {
            log.warn("failed to get total memory usage.", ignored);
        }
        return -1;
    }

    public static Long getFreePhysicalMem() {
        if (!OpSystem.isLinux()) {
            return 0L;
        }
        try {
            List<String> lines = IOUtils.readLines(new FileInputStream(PROCFS_MEMINFO));
            String free = lines.get(1).split("\\s+")[1];
            return Long.valueOf(free);
        } catch (Exception ignored) {
            log.warn("failed to get total free memory.");
        }
        return 0L;
    }

    /**
     * calculate the disk usage at current filesystem
     *
     * @return disk usage, from 0.0 ~ 1.0
     */
    public static Double getDiskUsage() {
        if (!OpSystem.isLinux() && !OpSystem.isMac()) {
            return 0.0;
        }
        try {
            String output = SystemOperation.exec("df -h " + duHome);
            if (output != null) {
                String[] lines = output.split("[\\r\\n]+");
                if (lines.length >= 2) {
                    String[] parts = lines[1].split("\\s+");
                    if (parts.length >= 5) {
                        String pct = parts[4];
                        if (pct.endsWith("%")) {
                            return Integer.valueOf(pct.substring(0, pct.length() - 1)) / 100.0;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.warn("failed to get disk usage.");
        }
        return 0.0;
    }

    public static Pair<Long, Long> getNetRevSendCount() {
        Pair<Long, Long> pair = new Pair<>(0L, 0L);
        if (!OpSystem.isLinux()) {
            return pair;
        }
        ProcResourceParse procResourceParse = new ProcResourceParse(PROCFS_NETSTAT, new ResourceCallback() {
            @Override
            public Object execute(List<String> lines) throws Exception {
                Pair<Long, Long> pair = new Pair<>(0L, 0L);
                int receiveIndex = -1;
                int sendIndex = -1;
                for (String line : lines) {
                    String[] splitString = line.split("\\s+|\\||:");
                    List<String> strings = new ArrayList<>();
                    for (String str : splitString) {
                        if (str.trim().length() > 0) {
                            strings.add(str);
                        }
                    }
                    if (strings.size() > 0 && "face".equals(strings.get(0))) {
                        for (int i = 0; i < strings.size(); i++) {
                            if ("bytes".equals(strings.get(i))) {
                                if (receiveIndex == -1) {
                                    receiveIndex = i;
                                } else {
                                    sendIndex = i;
                                }
                            }
                        }
                        break;
                    }
                }

                if (receiveIndex != -1 && sendIndex != -1) {
                    for (String line : lines) {
                        String[] splitString = line.split("\\s+|\\||:");
                        List<String> strings = new ArrayList<>();
                        for (String str : splitString) {
                            if (str.trim().length() > 0) {
                                strings.add(str);
                            }
                        }
                        if (strings.size() > 0 && strings.get(0).startsWith("eth")) {
                            long rec = ParseUtil.parseLong(strings.get(receiveIndex));
                            long send = ParseUtil.parseLong(strings.get(sendIndex));
                            pair.setFirst(pair.getFirst() + rec);
                            pair.setSecond(pair.getSecond() + send);
                        }
                    }
                } else {
                    log.warn("receiveIndex {}, sendIndex {}", receiveIndex, sendIndex);
                }
                return pair;
            }
        });
        Object result = procResourceParse.getResource();
        if (result != null) {
            pair = (Pair<Long, Long>) result;
        }
        return pair;
    }

    public interface ResourceCallback {
        Object execute(List<String> lines) throws Exception;
    }

    static class ProcResourceParse {
        private ResourceCallback callback;
        private String file;

        public ProcResourceParse(String file, ResourceCallback callback) {
            this.callback = callback;
            this.file = file;
        }

        public Object getResource() {
            try {
                List<String> lines = IOUtils.readLines(new FileInputStream(file));
                return callback.execute(lines);
            } catch (Exception e) {
                log.warn("Error reading the stream ", e);
            }
            return null;
        }
    }
}
