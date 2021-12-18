/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax;

import java.io.*;
import java.net.URL;
import java.util.Properties;

/**
 * @author rolex
 * @since 2020
 */
public class PropertiesUtil {
    public static Properties loadProperties() throws IOException {
        Properties properties = new Properties();
        URL url = ClassLoader.getSystemResource("map-reduce.properties");
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(new File(url.getPath())));
        properties.load(new InputStreamReader(bufferedInputStream, "UTF-8"));
        return properties;
    }
}
