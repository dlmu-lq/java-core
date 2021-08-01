package net.im.provide.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.im.provide.AbstractPacket;
import net.im.provide.CommandEnum;

/**
 * @author liqiang
 * @description 登录请求包
 * @date 2020/9/8 上午7:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestPacket extends AbstractPacket {

    private static final long serialVersionUID = -3704276055638906254L;

    private String userName;

    private String imCode;

    private String imToken;

    @Override
    public CommandEnum getCommand() {
        return CommandEnum.LOGIN_REQUEST;
    }
}
