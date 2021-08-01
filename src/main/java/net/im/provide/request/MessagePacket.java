package net.im.provide.request;

import lombok.Data;
import net.im.provide.AbstractPacket;
import net.im.provide.CommandEnum;

/**
 * @author liqiang
 * @description 客户端发送消息请求包
 * @date 2020/9/9 上午7:42
 */
@Data
public class MessagePacket extends AbstractPacket {

    private static final long serialVersionUID = -4529678184403037680L;

    private Long fromUserId;

    private String fromUserName;

    private Long toUserId;

    private String toUserName;

    private String content;

    @Override
    public CommandEnum getCommand() {
        return CommandEnum.MESSAGE_REQUEST;
    }
}
