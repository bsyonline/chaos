package com.rolex.discovery.load;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class LinuxLoadChecker implements OSLoadChecker {
    private static final boolean exists_Bash = new File("/bin/bash").exists();
    private static final boolean exists_Cat = new File("/bin/cat").exists();
    private static final boolean exists_Grep = new File("/bin/grep").exists();
    private static final boolean exists_Meminfo = new File("/proc/meminfo").exists();
    private static final boolean exists_LoadAvg = new File("/proc/loadavg").exists();

    @Override
    public void fillCpuLoad(Props props) {
        if (exists_Bash && exists_Cat && exists_LoadAvg) {
            ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", "/bin/cat /proc/loadavg");
            try {
                ArrayList<String> output = new ArrayList<>();
                Process process = processBuilder.start();
                process.waitFor();
                InputStream inputStream = process.getInputStream();
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        output.add(line);
                    }
                } finally {
                    inputStream.close();
                }

                // process the output from bash call.
                if (output.size() > 0) {
                    String[] splitedresult = output.get(0).split("\\s+");
                    double cpuUsage = 0.0;

                    try {
                        cpuUsage = Double.parseDouble(splitedresult[0]);
                    } catch (NumberFormatException e) {
                        log.error("yielding 0.0 for CPU usage as output is invalid -" + output.get(0));
                    }

                    if (log.isDebugEnabled()) {
                        log.debug("System load: {}", cpuUsage);
                    }
                    props.put(CPU_LOAD, cpuUsage);
                }
            } catch (Exception ex) {
                log.error("failed fetch system load info as exception is captured when fetching result from bash call.", ex);
            }
        } else {
            log.error("failed fetch system load info, one or more files from the following list are missing - '/bin/bash'," + "'/bin/cat'," + "'/proc/loadavg'");
        }
    }

    @Override
    public void fillMemoryLoad(Props props) {
        if (exists_Bash && exists_Cat && exists_Grep && exists_Meminfo) {
            ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", "/bin/cat /proc/meminfo | grep -E \"^MemTotal:|^MemFree:|^Buffers:|^Cached:|^SwapCached:\"");
            try {
                List<String> output = new ArrayList<>();
                Process process = processBuilder.start();
                process.waitFor();
                InputStream inputStream = process.getInputStream();
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        output.add(line);
                    }
                } finally {
                    inputStream.close();
                }

                long totalMemory = 0;
                long totalFreeMemory = 0;
                Long parsedResult = (long) 0;

                // process the output from bash call.
                // we expect the result from the bash call to be something like following -
                // MemTotal:       65894264 kB
                // MemFree:        57753844 kB
                // Buffers:          305552 kB
                // Cached:          3802432 kB
                // SwapCached:            0 kB
                // Note : total free memory = freeMemory + cached + buffers + swapCached
                // TODO : think about merging the logic in systemMemoryInfo as the logic is similar
                if (output.size() == 5) {
                    for (String result : output) {
                        // find the total memory and value the variable.
                        parsedResult = extractMemoryInfo("MemTotal", result);
                        if (null != parsedResult) {
                            totalMemory = parsedResult;
                            continue;
                        }

                        // find the free memory.
                        parsedResult = extractMemoryInfo("MemFree", result);
                        if (null != parsedResult) {
                            totalFreeMemory += parsedResult;
                            continue;
                        }

                        // find the Buffers.
                        parsedResult = extractMemoryInfo("Buffers", result);
                        if (null != parsedResult) {
                            totalFreeMemory += parsedResult;
                            continue;
                        }

                        // find the Cached.
                        parsedResult = extractMemoryInfo("SwapCached", result);
                        if (null != parsedResult) {
                            totalFreeMemory += parsedResult;
                            continue;
                        }

                        // find the Cached.
                        parsedResult = extractMemoryInfo("Cached", result);
                        if (null != parsedResult) {
                            totalFreeMemory += parsedResult;
                            continue;
                        }
                    }
                } else {
                    log.error("failed to get total/free memory info as the bash call returned invalid result." + String.format(" Output from the bash call - %s ", output.toString()));
                }

                // the number got from the proc file is in KBs we want to see the number in MBs so we are dividing it by 1024.
                props.put(REMAINING_MEMORY_IN_MB, totalFreeMemory / KBYTE_TO_MB);
                props.put(REMAINING_MEMORY_IN_PERCENT, totalMemory == 0 ? 0 : ((double) totalFreeMemory / (double) totalMemory) * 100);
            } catch (Exception ex) {
                log.error("failed fetch system memory info as exception is captured when fetching result from bash call.", ex);
            }
        } else {
            log.error("failed fetch system memory info, one or more files from the following list are missing -  " + "'/bin/bash'," + "'/bin/cat'," + "'/proc/loadavg'");
        }
    }

    private Long extractMemoryInfo(String field, String result) {
        Long returnResult = null;
        if (null != result && null != field && result.matches(String.format("^%s:.*", field)) && result.split("\\s+").length > 2) {
            try {
                returnResult = Long.parseLong(result.split("\\s+")[1]);
                log.debug(field + ":" + returnResult);
            } catch (NumberFormatException e) {
                returnResult = 0L;
                log.error(String.format("yielding 0 for %s as output is invalid - %s", field, result));
            }
        }
        return returnResult;
    }
}
