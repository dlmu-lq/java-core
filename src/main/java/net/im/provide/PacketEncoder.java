package net.im.provide;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author liqiang
 * @description Packet 编码为 ByteBuf
 * @date 2020/9/10 下午11:22
 */
public class PacketEncoder extends MessageToByteEncoder<AbstractPacket> {
    @Override
    protected void encode(ChannelHandlerContext ctx, AbstractPacket msg, ByteBuf out) throws Exception {
        Protocol.encode(out, msg);
    }
}
