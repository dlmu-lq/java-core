package top.itlq.java.net.im;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author liqiang
 * @description 登录
 * @date 2020/9/8 上午7:32
 */
@Data
@AllArgsConstructor
public class LoginPacket extends AbstractPacket{

    private String imCode;

    private String imToken;

    @Override
    public CommandEnum getCommand() {
        return CommandEnum.LOGIN;
    }
}
