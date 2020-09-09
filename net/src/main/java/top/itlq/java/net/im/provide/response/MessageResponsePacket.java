package top.itlq.java.net.im.provide.response;

import lombok.Data;
import top.itlq.java.net.im.provide.AbstractPacket;
import top.itlq.java.net.im.provide.CommandEnum;

/**
 * @author liqiang
 * @description 服务端消息响应包
 * @date 2020/9/9 上午7:42
 */
@Data
public class MessageResponsePacket extends AbstractPacket {

    private static final long serialVersionUID = -8725169459416642527L;

    private String content;

    @Override
    public CommandEnum getCommand() {
        return CommandEnum.MESSAGE_RESPONSE;
    }
}
