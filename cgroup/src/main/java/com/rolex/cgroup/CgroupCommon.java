package com.rolex.cgroup;

import java.io.File;
import java.io.IOException;
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
public class CgroupCommon implements CgroupCommonOperation {

    public static final String TASKS = "/tasks";

    public static final String CGROUP_PROCS = "/cgroup.procs";

    private final Hierarchy hierarchy;

    private final String name;

    private final String dir;

    private final CgroupCommon parent;

    private final Map<SubSystemType, CgroupCore> cores;

    private final boolean isRoot;

    private final Set<CgroupCommon> children = new HashSet<CgroupCommon>();

    public String getName() {
        return name;
    }

    public String getDir() {
        return dir;
    }

    public CgroupCommon getParent() {
        return parent;
    }

    public Set<CgroupCommon> getChildren() {
        return children;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public Hierarchy getHierarchy() {
        return hierarchy;
    }

    public Map<SubSystemType, CgroupCore> getCores() {
        return cores;
    }

    /**
     * rootCgroup
     */
    public CgroupCommon(Hierarchy hierarchy, String dir) {
        this.name = "";
        this.hierarchy = hierarchy;
        this.parent = null;
        this.dir = dir;
        this.init();
        cores = CgroupCoreFactory.getInstance(this.hierarchy.getSubSystems(), this.dir);
        this.isRoot = true;
    }

    public CgroupCommon(String name, Hierarchy hierarchy, CgroupCommon parent) {
        this.name = parent.getName() + "/" + name;
        this.hierarchy = hierarchy;
        this.parent = parent;
        this.dir = parent.getDir() + "/" + name;
        this.init();
        cores = CgroupCoreFactory.getInstance(this.hierarchy.getSubSystems(), this.dir);
        this.isRoot = false;
    }

    private void init() {
        File file = new File(this.dir);
        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        for (File child : files) {
            if (child.isDirectory()) {
                this.children.add(new CgroupCommon(child.getName(), this.hierarchy, this));
            }
        }
    }

    @Override
    public void addTask(int taskId) throws IOException {
        CgroupUtils.writeFileByLine(ParseUtil.getDir(this.dir, TASKS), String.valueOf(taskId));
    }

    @Override
    public Set<Integer> getTasks() throws IOException {
        List<String> stringTasks = CgroupUtils.readFileByLine(ParseUtil.getDir(this.dir, TASKS));
        Set<Integer> tasks = new HashSet<>();
        for (String task : stringTasks) {
            tasks.add(Integer.valueOf(task));
        }
        return tasks;
    }

    @Override
    public void addProcs(int pid) throws IOException {
        CgroupUtils.writeFileByLine(ParseUtil.getDir(this.dir, CGROUP_PROCS), String.valueOf(pid));
    }

    @Override
    public Set<Integer> getPids() throws IOException {
        List<String> stringPids = CgroupUtils.readFileByLine(ParseUtil.getDir(this.dir, CGROUP_PROCS));
        Set<Integer> pids = new HashSet<>();
        for (String task : stringPids) {
            pids.add(Integer.valueOf(task));
        }
        return pids;
    }

    public void delete() throws IOException {
        this.free();
        if (!this.isRoot) {
            this.parent.getChildren().remove(this);
        }
    }

    private void free() throws IOException {
        for (CgroupCommon child : this.children) {
            child.free();
        }
        if (this.isRoot) {
            return;
        }
        Set<Integer> tasks = this.getTasks();
        if (tasks != null) {
            for (Integer task : tasks) {
                this.parent.addTask(task);
            }
        }
        CgroupUtils.deleteDir(this.dir);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dir == null) ? 0 : dir.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this.equals(obj)) {
            return true;
        }
        if (this == obj) {
            return false;
        }
        return super.equals(obj);
    }
}
