package com.rolex.registry;

import com.ksyun.kbdp.dts.model.event.protobuf.DtsMsgWrapper;
import com.rolex.rpc.model.proto.MsgProto;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class App implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
//                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
//                        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                        pipeline.addLast("idle-state-handler", new IdleStateHandler(5, 0, 0));
//                        pipeline.addLast("decoder", new MsgDecoder());
//                        pipeline.addLast("encoder", new MsgEncoder());
                        pipeline.addLast(new ProtobufVarint32FrameDecoder());
                        pipeline.addLast(new ProtobufDecoder(DtsMsgWrapper.DtsMsg.getDefaultInstance()));
                        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
                        pipeline.addLast(new ProtobufEncoder());
                        pipeline.addLast("client-handler", new ClientHandler());
                    }
                });
        log.info("starting connect to server {}:{}", args[0], args[1]);
        ChannelFuture future = bootstrap.connect(args[0], Integer.valueOf(args[1])).sync();
        future.channel().closeFuture();
    }
}
