package top.itlq.java.net.im.provide;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import top.itlq.java.net.im.provide.request.LoginRequestPacket;
import top.itlq.java.net.im.provide.request.MessageRequestPacket;
import top.itlq.java.net.im.provide.response.LoginResponsePacket;
import top.itlq.java.net.im.provide.response.MessageResponsePacket;

import java.util.Optional;

/**
 * @author liqiang
 * @description 通信指令枚举
 * @date 2020/9/8 上午7:12
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CommandEnum {
    /**
     * 指令枚举
     */
    LOGIN_REQUEST((short)1, "登录请求", LoginRequestPacket.class),
    LOGIN_RESPONSE((short)2, "登录响应", LoginResponsePacket.class),
    MESSAGE_REQUEST((short)3, "发消息请求", MessageRequestPacket.class),
    MESSAGE_RESPONSE((short)4, "消息响应", MessageResponsePacket.class),
    ;

    private short value;

    private String desc;

    private Class<? extends AbstractPacket> packetClazz;

    public static Optional<Class<? extends AbstractPacket>> getClass(short value){
        for (CommandEnum commandEnum: values()) {
            if (value == commandEnum.value){
                return Optional.of(commandEnum.packetClazz);
            }
        }
        return Optional.empty();
    }
}
