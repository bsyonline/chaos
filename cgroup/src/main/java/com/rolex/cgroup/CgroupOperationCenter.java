package com.rolex.cgroup;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
public class CgroupOperationCenter implements CgroupOperation {
    private static CgroupOperationCenter instance;

    private CgroupOperationCenter() {
    }

    /**
     * Thread unsafe
     */
    public synchronized static CgroupOperationCenter getInstance() {
        if (instance == null) {
            instance = new CgroupOperationCenter();
        }
        return CgroupUtils.enabled() ? instance : null;
    }

    @Override
    public List<Hierarchy> getHierarchies() {
        Map<String, Hierarchy> hierarchies = new HashMap<>();
        FileReader reader = null;
        BufferedReader br = null;
        try {
            reader = new FileReader(Constants.MOUNT_STATUS_FILE);
            br = new BufferedReader(reader);
            String str;
            while ((str = br.readLine()) != null) {
                String[] strSplit = str.split(" ");
                if (!"cgroup".equals(strSplit[2])) {
                    continue;
                }
                String name = strSplit[0];
                String type = strSplit[3];
                String dir = strSplit[1];
                Hierarchy h = new Hierarchy(name, CgroupUtils.analyse(type), dir);
                hierarchies.put(type, h);
            }
            return new ArrayList<>(hierarchies.values());
        } catch (Exception e) {
            log.error("Get hierarchies error", e);
        } finally {
            CgroupUtils.close(reader, br);
        }
        return null;
    }

    @Override
    public boolean enabled(SubSystemType subsystem) {
        Set<SubSystem> subSystems = this.getSubSystems();
        for (SubSystem subSystem : subSystems) {
            if (subSystem.getType() == subsystem) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Hierarchy busy(String traceId, SubSystemType subsystem) {
        List<Hierarchy> hierarchies = this.getHierarchies();
        for (Hierarchy hierarchy : hierarchies) {
            for (SubSystemType type : hierarchy.getSubSystems()) {
                if (type == subsystem) {
                    return hierarchy;
                }
            }
        }
        return null;
    }

    @Override
    public Set<SubSystem> getSubSystems() {
        Set<SubSystem> subSystems = new HashSet<>();
        FileReader reader = null;
        BufferedReader br = null;
        try {
            reader = new FileReader(Constants.CGROUP_STATUS_FILE);
            br = new BufferedReader(reader);
            String str;
            while ((str = br.readLine()) != null) {
                String[] split = str.split("\t");
                SubSystemType type = SubSystemType.getSubSystem(split[0]);
                if (type == null) {
                    continue;
                }
                subSystems.add(new SubSystem(type, Integer.valueOf(split[1]), Integer.valueOf(split[2]),
                        Integer.valueOf(split[3]) == 1));
            }
            return subSystems;
        } catch (Exception e) {
            log.error("Get subSystems error ", e);
        } finally {
            CgroupUtils.close(reader, br);
        }
        return null;
    }

    @Override
    public Hierarchy mounted(String traceId, Hierarchy hierarchy) {
        List<Hierarchy> hierarchies = this.getHierarchies();
        if (CgroupUtils.dirExists(hierarchy.getDir())) {
            for (Hierarchy h : hierarchies) {
                if (h.equals(hierarchy)) {
                    return h;
                }
            }
        }
        return null;
    }

    @Override
    public void mount(String traceId, Hierarchy hierarchy) throws IOException {
        if (this.mounted(traceId, hierarchy) != null) {
            log.error(hierarchy.getDir() + " is mounted");
            return;
        }
        Set<SubSystemType> subsystems = hierarchy.getSubSystems();
        for (SubSystemType type : subsystems) {
            if (this.busy(traceId, type) != null) {
                log.error("subsystem: " + type.name() + " is busy");
                subsystems.remove(type);
            }
        }
        if (subsystems.size() == 0) {
            return;
        }
        if (!CgroupUtils.dirExists(hierarchy.getDir())) {
            new File(hierarchy.getDir()).mkdirs();
        }
        String subSystems = CgroupUtils.reAnalyse(subsystems);
        SystemOperation.mount(traceId, subSystems, hierarchy.getDir(), "cgroup", subSystems);

    }

    @Override
    public void umount(String traceId, Hierarchy hierarchy) throws IOException {
        if (this.mounted(traceId, hierarchy) != null) {
            hierarchy.getRootCgroups().delete();
            SystemOperation.umount(hierarchy.getDir());
            CgroupUtils.deleteDir(hierarchy.getDir());
        }
    }

    @Override
    public void create(String traceId, CgroupCommon cgroup) throws SecurityException {
        if (cgroup.isRoot()) {
            log.error("{} You can't create rootCgroup in this function", traceId);
            return;
        }
        CgroupCommon parent = cgroup.getParent();
        while (parent != null) {
            if (!CgroupUtils.dirExists(parent.getDir())) {
                log.error(parent.getDir() + " does not exist");
                return;
            }
            parent = parent.getParent();
        }
        Hierarchy h = cgroup.getHierarchy();
        if (mounted(traceId, h) == null) {
            log.error(h.getDir() + " is not mounted");
            return;
        }
        if (CgroupUtils.dirExists(cgroup.getDir())) {
            log.error(cgroup.getDir() + " exists");
            return;
        }
        (new File(cgroup.getDir())).mkdir();
        log.debug("{} mkdir {}", traceId, cgroup.getDir());
    }

    @Override
    public void delete(String traceId, CgroupCommon cgroup) throws IOException {
        cgroup.delete();
    }
}
