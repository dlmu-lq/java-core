package top.itlq.java.net.im.provide;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author liqiang04
 * @description 用户信息
 * @date 2020/9/13 5:42 下午
 */
@Data
public class User {

    private static AtomicLong atomicLong = new AtomicLong();

    private Long id = atomicLong.getAndIncrement();

    private String userName;

}
