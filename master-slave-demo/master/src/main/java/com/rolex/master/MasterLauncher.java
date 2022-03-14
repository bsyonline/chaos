package com.rolex.master;

import com.rolex.common.util.NetUtils;
import com.rolex.master.manager.ExecutorManager;
import com.rolex.master.server.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@SpringBootApplication
public class MasterLauncher implements CommandLineRunner {

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(MasterLauncher.class).web(WebApplicationType.SERVLET).run(args);
    }

    @Value("${tcp.port}")
    int port;
    @Autowired
    ExecutorManager executorManager;

    @Override
    public void run(String... args) throws Exception {
        new NettyServer(NetUtils.getSiteIP(), port).executorManager(executorManager).start();
    }
}
