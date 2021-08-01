package net.im.server;

import com.google.common.collect.Maps;
import io.netty.channel.Channel;
import net.im.provide.AttributeKeys;
import net.im.provide.User;

import java.util.Map;
import java.util.Objects;

/**
 * @author liqiang04
 * @description 保存用户连接信息
 * @date 2020/9/13 5:39 下午
 */
public class SessionUtils {

    private static Map<Long, Channel> sessions = Maps.newConcurrentMap();

    public static void bindSession(User user, Channel channel){
        sessions.put(user.getId(), channel);
        channel.attr(AttributeKeys.USER).set(user);
    }

    public static void unBindSession(Channel channel){
        if(hasLogin(channel)){
            User user = channel.attr(AttributeKeys.USER).get();
            channel.attr(AttributeKeys.USER).set(null);
            sessions.remove(user.getId());
        }
    }

    public static boolean hasLogin(Channel channel){
        return Objects.nonNull(channel.attr(AttributeKeys.USER));
    }

    public static User getUser(Channel channel){
        return channel.attr(AttributeKeys.USER).get();
    }

    public static Channel getChannel(Long userId){
        return sessions.get(userId);
    }
}
