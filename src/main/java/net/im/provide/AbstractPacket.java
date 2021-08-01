package net.im.provide;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liqiang
 * @description 数据包基类
 * @date 2020/9/8 上午7:17
 */
@Data
public abstract class AbstractPacket implements Serializable {

    private static final long serialVersionUID = -1054757916875801529L;

    /**
     * 子类实现，返回对应子类指令类型
     * @return
     */
    public abstract CommandEnum getCommand();
}
