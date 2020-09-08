package top.itlq.java.net.im;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * @author liqiang
 * @description 通信协议，编码解码
 * 协议基本形式 魔数（4B） + 版本（1B） + 指令（2B） + 数据长度（4B） + 数据（？）
 * @date 2020/9/8 上午7:20
 */
public class Protocol {

    /**
     * 魔数
     */
    private static final Integer MAGIC_NUMBER = 0x100320;


    /**
     * 将数据包编码成要发送的数据
     * @param packet
     * @return
     */
    public static ByteBuf encode(AbstractPacket packet){
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        // 魔数
        byteBuf.writeInt(MAGIC_NUMBER);
        // 版本
        byteBuf.writeByte(packet.getVersion());
        // 指令
        byteBuf.writeShort(packet.getCommand().getValue());
        // 数据、长度
        byte[] bytes = new Gson().toJson(packet).getBytes(StandardCharsets.UTF_8);
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
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
        // 版本
        byteBuf.skipBytes(1);
        // 指令
        short commandValue = byteBuf.readShort();
        Optional<Class<? extends AbstractPacket>> optionalClass = CommandEnum.classFromValue(commandValue);
        if(!optionalClass.isPresent()){
            return null;
        }
        // 数据、长度
        int dataLength = byteBuf.readInt();
        byte[] data = new byte[dataLength];
        byteBuf.readBytes(data);
        return new Gson().fromJson(new String(data, StandardCharsets.UTF_8), optionalClass.get());
    }

    public static void main(String[] args) {
        LoginPacket loginPacket = new LoginPacket("1", "3");
        ByteBuf encode = encode(loginPacket);
        System.out.println(decode(encode));
    }
}
