package top.itlq.java.net.im.provide;

import io.netty.util.AttributeKey;

/**
 * @author liqiang
 * @description
 * @date 2020/9/9 上午7:48
 */
public interface AttributeKeys {

    /**
     * 一些属性枚举
     */
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
}
