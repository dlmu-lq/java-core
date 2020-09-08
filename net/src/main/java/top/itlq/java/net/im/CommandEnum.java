package top.itlq.java.net.im;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
    LOGIN((short)1, "登录", LoginPacket.class),
    ;

    private short value;

    private String desc;

    private Class<? extends AbstractPacket> packetClazz;

    public static Optional<Class<? extends AbstractPacket>> classFromValue(short value){
        for (CommandEnum commandEnum: values()) {
            if (value == commandEnum.value){
                return Optional.of(commandEnum.packetClazz);
            }
        }
        return Optional.empty();
    }
}
