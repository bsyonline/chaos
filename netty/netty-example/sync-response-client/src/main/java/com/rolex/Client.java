package com.rolex;

import com.rolex.codec.RpcDecoder;
import com.rolex.codec.RpcEncoder;
import com.rolex.msg.Request;
import com.rolex.msg.Response;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@SpringBootApplication
public class Client implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Client.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.AUTO_READ, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(
                            new RpcDecoder(Response.class),
                            new RpcEncoder(Request.class),
                            new MyClientHandler());
                }
            });
            ChannelFuture f = b.connect("127.0.0.1", 7397).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
