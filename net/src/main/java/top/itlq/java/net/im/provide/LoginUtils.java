package top.itlq.java.net.im.provide;

import io.netty.channel.Channel;

/**
 * @author liqiang
 * @description 登录标记和判断
 * @date 2020/9/9 上午7:48
 */
public class LoginUtils {

    public static void markAsLogin(Channel channel){
        channel.attr(AttributeKeys.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel){
        Boolean o = channel.attr(AttributeKeys.LOGIN).get();
        return o != null && o;
    }
}
