package top.itlq.java.net.im.provide.request;

import lombok.Data;
import top.itlq.java.net.im.provide.AbstractPacket;
import top.itlq.java.net.im.provide.CommandEnum;

/**
 * @author liqiang
 * @description 客户端发送消息请求包
 * @date 2020/9/9 上午7:42
 */
@Data
public class MessageRequestPacket extends AbstractPacket {

    private static final long serialVersionUID = -4529678184403037680L;

    private String content;

    @Override
    public CommandEnum getCommand() {
        return CommandEnum.MESSAGE_REQUEST;
    }
}
