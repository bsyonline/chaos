package com.rolex;

import com.rolex.codec.RpcDecoder;
import com.rolex.codec.RpcEncoder;
import com.rolex.feture.SyncWrite;
import com.rolex.msg.Request;
import com.rolex.msg.Response;
import com.rolex.util.ChannelManager;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
        SpringApplication.run(Server.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(
                                    new RpcDecoder(Request.class),
                                    new RpcEncoder(Response.class),
                                    new MyServerHandler());
                        }
                    });

            ChannelFuture f = null;
            f = b.bind(7397).sync();
            f.channel().closeFuture().sync();


        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    @GetMapping("/send")
    public String send() throws Exception {
        Request request = new Request();
        request.setResult("查询用户信息");
        SyncWrite s = new SyncWrite();
        Response response = s.writeAndSync(ChannelManager.CHANNEL_MAP.get("client"), request, 1000);
        log.info("调用结果：" + response);
        return "OK";
    }
}
