package top.itlq.java.net.im;

import lombok.Data;

/**
 * @author liqiang
 * @description 数据包基类
 * @date 2020/9/8 上午7:17
 */
@Data
public abstract class AbstractPacket {

    private Byte version = 1;

    /**
     * 子类实现，返回对应子类指令类型
     * @return
     */
    public abstract CommandEnum getCommand();
}
