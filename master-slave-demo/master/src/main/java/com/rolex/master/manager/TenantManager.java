package com.rolex.master.manager;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public class TenantManager {
    private static Map<String, List<String>> tenants = Maps.newConcurrentMap();

    public static List<String> getTenants() {
        tenants.put("tenant", Lists.newArrayList("tom", "john"));
        return tenants.get("tenant");
    }
}
