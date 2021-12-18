package com.rolex.dfs.datanode;

import com.rolex.dfs.datanode.rpc.NameNodeOfferService;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.annotation.PostConstruct;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@SpringBootApplication
public class DataNode implements IStoppable {

    /**
     * 是否还在运行
     */
    private volatile Boolean shouldRun;
    /**
     * 负责跟一组NameNode通信的组件
     */
    private NameNodeOfferService offerService;

    /**
     * 初始化DataNode
     */
    private void initialize() {
        this.shouldRun = true;
        this.offerService = new NameNodeOfferService();
        this.offerService.start();
    }

    /**
     * 运行DataNode
     */
    @PostConstruct
    public void run() {
        initialize();
    }

    public static void main(String[] args) {
        Thread.currentThread().setName("datanode");
        new SpringApplicationBuilder(DataNode.class).web(WebApplicationType.NONE).run(args);
    }

    @Override
    public void stop(String cause) {
        close(cause);
    }

    private void close(String cause) {
        if(shouldRun){
            try {
                offerService.unRegistry();
                offerService.shutdown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
