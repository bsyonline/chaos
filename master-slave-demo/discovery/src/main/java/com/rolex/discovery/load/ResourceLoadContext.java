package com.rolex.discovery.load;


public class ResourceLoadContext {
    private static final int DEFAULT_CACHE_TIME_IN_MILLISECONDS = 60000;

    private boolean cache;
    private Props params;
    private ResourceType resourceType;
    private long cacheTimeInMillSec;

    public ResourceLoadContext(ResourceType resourceType, long cacheTimeInMillSec) {
        this.resourceType = resourceType;
        this.cacheTimeInMillSec = cacheTimeInMillSec;
        if (cacheTimeInMillSec > 0) {
            this.cache = true;
        } else {
            this.cache = false;
        }
    }

    public ResourceLoadContext(ResourceType resourceType, long cacheTimeInMillSec, Props params) {
        this.resourceType = resourceType;
        this.cacheTimeInMillSec = cacheTimeInMillSec;
        if (cacheTimeInMillSec > 0) {
            this.cache = true;
        } else {
            this.cache = false;
        }
        this.params = params;
    }

    public static ResourceLoadContext getOsCheckerResourceLoadContext() {
        return new ResourceLoadContext(ResourceType.Executor, DEFAULT_CACHE_TIME_IN_MILLISECONDS);
    }

    public static ResourceLoadContext getK8sOrYarnResourceLoadContext(Props params) {
        return new ResourceLoadContext(ResourceType.K8S, 0, params);
    }

    public <T> T get(String key) {
        Object res = params.get(key);
        if (res != null) {
            return (T) res;
        }
        return null;
    }

    public void put(String key, Object val) {
        params.put(key, val);
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public boolean isCache() {
        return cache;
    }

    public long getCacheTimeInMillSec() {
        return cacheTimeInMillSec;
    }

    public ResourceLoadContext setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
        return this;
    }

    public ResourceLoadContext setCache(boolean cache) {
        this.cache = cache;
        return this;
    }
}