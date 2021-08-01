package net.im.provide.response;

import lombok.Data;
import net.im.provide.AbstractPacket;
import net.im.provide.CommandEnum;

/**
 * @author liqiang
 * @description 登录响应包
 * @date 2020/9/8 上午7:32
 */
@Data
public class LoginResponsePacket extends AbstractPacket {

    private static final long serialVersionUID = -3467304232402640262L;

    private Boolean success;

    private String reason;

    @Override
    public CommandEnum getCommand() {
        return CommandEnum.LOGIN_RESPONSE;
    }
}
