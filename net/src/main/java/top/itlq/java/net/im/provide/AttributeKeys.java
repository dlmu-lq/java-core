package top.itlq.java.net.im.provide;

import io.netty.util.AttributeKey;

/**
 * @author liqiang
 * @description
 * @date 2020/9/9 上午7:48
 */
public interface AttributeKeys {

    /**
     * 是否登录
     */
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");

    /**
     * 连接用户id
     */
    AttributeKey<User> USER = AttributeKey.newInstance("user");

}
