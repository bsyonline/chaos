package com.rolex.slave;

import com.rolex.slave.client.NettyClient;
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
public class SlaveLauncher implements CommandLineRunner {
    public static void main(String[] args) throws InterruptedException {
        new SpringApplicationBuilder(SlaveLauncher.class).web(WebApplicationType.NONE).run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        new NettyClient().start();
    }
}
