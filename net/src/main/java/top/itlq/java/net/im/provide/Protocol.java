package top.itlq.java.net.im.provide;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import top.itlq.java.net.im.provide.request.LoginRequestPacket;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * @author liqiang
 * @description 通信协议，编码解码
 * 协议基本形式 魔数（4B） + 指令（2B） + 数据长度（4B） + 数据（？）
 * @date 2020/9/8 上午7:20
 */
public class Protocol {

    /**
     * 魔数
     */
    private static final int MAGIC_NUMBER = 0x100320;


    /**
     * 将数据包编码成要发送的数据
     * @param packet
     * @return
     */
    public static ByteBuf encode(AbstractPacket packet){
        return encode(ByteBufAllocator.DEFAULT, packet);
    }


    /**
     * 将数据包编码成要发送的数据
     * @param packet
     * @return
     */
    public static ByteBuf encode(ByteBufAllocator allocator, AbstractPacket packet){
        ByteBuf byteBuf = allocator.ioBuffer();
        encode(byteBuf, packet);
        return byteBuf;
    }

    /**
     * 将数据包编码成要发送的数据放在传入的ByteBuf中
     * @param byteBuf
     * @param packet
     */

    public static void encode(ByteBuf byteBuf, AbstractPacket packet){
        // 魔数
        byteBuf.writeInt(MAGIC_NUMBER);
        // 指令
        byteBuf.writeShort(packet.getCommand().getValue());
        // 数据、长度
        byte[] bytes = new Gson().toJson(packet).getBytes(StandardCharsets.UTF_8);
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }


    /**
     * 将接收的数据解码成数据包
     * @param byteBuf
     * @return
     */
    public static AbstractPacket decode(ByteBuf byteBuf){
        // 魔数
        int i = byteBuf.readInt();
        if(MAGIC_NUMBER != i){
            return null;
        }
        // 指令
        short commandValue = byteBuf.readShort();
        Optional<Class<? extends AbstractPacket>> optionalClass = CommandEnum.getClass(commandValue);
        if(!optionalClass.isPresent()){
            return null;
        }
        // 数据、长度
        int dataLength = byteBuf.readInt();
        byte[] data = new byte[dataLength];
        byteBuf.readBytes(data);
        return new Gson().fromJson(new String(data, StandardCharsets.UTF_8), optionalClass.get());
    }

    /**
     * 根据协议拆包和校验魔数类
     * TODO 数据包的顺序会不会出现错乱？
     */
    public static class Spliter extends LengthFieldBasedFrameDecoder{
        public Spliter() {
            super(Integer.MAX_VALUE, 6, 4);
        }

        /**
         * 继承decode方法根据协议魔数判断丢弃无效连接
         * @param ctx
         * @param in
         * @return
         * @throws Exception
         */
        @Override
        protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
            if(in.getInt(in.readerIndex()) != MAGIC_NUMBER){
                ctx.channel().close();
                return null;
            }
            return super.decode(ctx, in);
        }
    }

    public static void main(String[] args) {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket("1", "3");
        ByteBuf encode = encode(loginRequestPacket);
        System.out.println(decode(encode));
    }
}
