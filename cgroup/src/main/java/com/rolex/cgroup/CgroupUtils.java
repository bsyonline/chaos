package com.rolex.cgroup;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
 * @since 2021
 */
@Slf4j
public class CgroupUtils {
    public static void deleteDir(String dir) {
        try {
            String cmd = "rmdir " + dir;
            SystemOperation.exec(cmd);
        } catch (IOException e) {
            log.error("rm " + dir + " fail!", e);
        }
    }

    public static boolean fileExists(String dir) {
        File file = new File(dir);
        return file.exists();
    }

    public static boolean dirExists(String dir) {
        File file = new File(dir);
        return file.isDirectory();
    }
    public static boolean enabled() {
        return CgroupUtils.fileExists(Constants.CGROUP_STATUS_FILE);
    }

    /**
     * 解析字符串为子系统类型集合
     * @param str cpu,cpuset,memory
     * @return
     */
    public static Set<SubSystemType> analyse(String str) {
        Set<SubSystemType> result = new HashSet<>();
        String[] subSystems = str.split(",");
        for (String subSystem : subSystems) {
            SubSystemType type = SubSystemType.getSubSystem(subSystem);
            if (type != null) {
                result.add(type);
            }
        }
        return result;
    }

    /**
     * 子系统类型集合解析为字符串，逗号分隔
     * @param subSystems
     * @return cpu,cpuset,memory
     */
    public static String reAnalyse(Set<SubSystemType> subSystems) {
        StringBuilder sb = new StringBuilder();
        if (subSystems.size() == 0) {
            return sb.toString();
        }
        for (SubSystemType type : subSystems) {
            sb.append(type.name()).append(",");
        }
        return sb.toString().substring(0, sb.length() - 1);
    }

    public static List<String> readFileByLine(String fileDir) throws IOException {
        List<String> result = new ArrayList<>();
        FileReader fileReader = null;
        BufferedReader reader = null;
        try {
            File file = new File(fileDir);
            fileReader = new FileReader(file);
            reader = new BufferedReader(fileReader);
            String tempString;
            while ((tempString = reader.readLine()) != null) {
                result.add(tempString);
            }
        } finally {
            CgroupUtils.close(fileReader, reader);
        }
        return result;
    }

    public static void writeFileByLine(String fileDir, List<String> strings) throws IOException {
        FileWriter writer = null;
        BufferedWriter bw = null;
        try {
            File file = new File(fileDir);
            if (!file.exists()) {
                log.error(fileDir + " is no existed");
                return;
            }
            writer = new FileWriter(file, true);
            bw = new BufferedWriter(writer);
            for (String string : strings) {
                bw.write(string);
                bw.newLine();
                bw.flush();
            }
        } finally {
            CgroupUtils.close(writer, bw);
        }
    }

    public static void writeFileByLine(String fileDir, String string) throws IOException {
        FileWriter writer = null;
        BufferedWriter bw = null;
        try {
            File file = new File(fileDir);
            if (!file.exists()) {
                log.error(fileDir + " is no existed");
                return;
            }
            writer = new FileWriter(file, true);
            bw = new BufferedWriter(writer);
            bw.write(string);
            bw.newLine();
            bw.flush();

        } finally {
            CgroupUtils.close(writer, bw);
        }
    }

    public static void close(FileReader reader, BufferedReader br) {
        try {
            if (reader != null) {
                reader.close();
            }
            if (br != null) {
                br.close();
            }
        } catch (IOException ignored) {
        }
    }

    public static void close(FileWriter writer, BufferedWriter bw) {
        try {
            if (writer != null) {
                writer.close();
            }
            if (bw != null) {
                bw.close();
            }
        } catch (IOException ignored) {
        }
    }
}
