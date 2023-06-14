package com.rolex;

import com.rolex.feture.SyncWriteFuture;
import com.rolex.feture.SyncWriteMap;
import com.rolex.msg.Request;
import com.rolex.msg.Response;
import com.rolex.util.ChannelManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj){
        Response msg = (Response) obj;
        String requestId = msg.getRequestId();
        SyncWriteFuture future = (SyncWriteFuture) SyncWriteMap.syncKey.get(requestId);
        if (future != null) {
            future.setResponse(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ChannelManager.CHANNEL_MAP.put("client", ctx.channel());
    }
}
