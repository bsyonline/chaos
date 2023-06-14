package com.rolex;

import com.rolex.handler.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.remoting.netty.ResponseFuture;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Slf4j
@RestController
@SpringBootApplication
public class Server implements CommandLineRunner {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Server.class).web(WebApplicationType.SERVLET).run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_SNDBUF, 65535)
                    .childOption(ChannelOption.SO_RCVBUF, 65535)
//                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
//                            pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                            pipeline.addLast("server-handler", new ServerHandler());
                        }
                    })
                    .localAddress("localhost", 4321);

            ChannelFuture future = bootstrap.bind().sync();
            log.info("server started on port {}", 4321);
            future.channel().closeFuture();
//            setServerLocalRoutingInfo();
        } catch (Exception e) {
            throw new RuntimeException("netty server start failed", e);
        }
    }

    public static Map<String, Channel> channels = new HashMap();

    @GetMapping("/send")
    public String send() throws Exception {
        Channel channel = channels.get("channel");
//        ChannelFuture channelFuture = channel.writeAndFlush("abc");
//        String result = sendSync(channel, "abc");
//        System.out.println(result);

        Request request = new Request();
        request.setRequestId("1");
        request.setResult("abc");

        Response response = new SyncWrite().writeAndSync(channel, request, 2000);
        System.out.println(response);

        return "OK";
    }




}
