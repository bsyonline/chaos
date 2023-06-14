package com.rolex.cgroup;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Slf4j
public class ProcessUtil {
    public static String launchProcess(final String command, final List<String> cmdlist,
                                       final Map<String, String> environment, boolean backend) throws IOException {
        if (backend) {
            new Thread(() -> {
                List<String> cmdWrapper = new ArrayList<>();

                cmdWrapper.add("nohup");
                cmdWrapper.addAll(cmdlist);
                cmdWrapper.add("&");

                try {
                    launchProcess(cmdWrapper, environment);
                } catch (IOException e) {
                    log.error("Failed to run nohup " + command + " &," + e.getCause(), e);
                }
            }).start();
            return null;
        } else {
            try {
                Process process = launchProcess(cmdlist, environment);

                StringBuilder sb = new StringBuilder();
                String output = getOutput(process.getInputStream());
                String errorOutput = getOutput(process.getErrorStream());
                sb.append(output);
                sb.append("\n");
                sb.append(errorOutput);

                int ret = process.waitFor();
                if (ret != 0) {
                    log.warn(command + " is terminated abnormally. ret={}, str={}", ret, sb.toString());
                }
                log.debug("command {}, ret {}, str={} :", command, ret, sb.toString());
                return sb.toString();
            } catch (Throwable e) {
                log.error("Failed to run " + command + ", " + e.getCause(), e);
            }

            return "";
        }
    }

    protected static java.lang.Process launchProcess(final List<String> cmdlist,
                                                     final Map<String, String> environment) throws IOException {
        ProcessBuilder builder = new ProcessBuilder(cmdlist);
        builder.redirectErrorStream(true);
        Map<String, String> processEvn = builder.environment();
        for (Map.Entry<String, String> entry : environment.entrySet()) {
            processEvn.put(entry.getKey(), entry.getValue());
        }

        return builder.start();
    }

    public static String getOutput(InputStream input) {
        BufferedReader in = new BufferedReader(new InputStreamReader(input));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = in.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
