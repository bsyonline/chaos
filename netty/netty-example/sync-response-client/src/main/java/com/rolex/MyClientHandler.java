package com.rolex;

import com.rolex.feture.SyncWriteFuture;
import com.rolex.feture.SyncWriteMap;
import com.rolex.msg.Request;
import com.rolex.msg.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
public class MyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) throws Exception {
        Request msg = (Request) obj;
        //反馈
        Response request = new Response();
        request.setRequestId(msg.getRequestId());
        request.setParam(msg.getResult() + " 请求成功");
        ctx.writeAndFlush(request);
        //释放
        ReferenceCountUtil.release(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
