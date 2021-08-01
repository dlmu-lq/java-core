package test.oom;


import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

/**
 * 使用cglib运行时继承加载类，测试方法区溢出
 * JVM参数：-XX:MaxPermSize=10m -XX:PermSize=10m 在jdk1.8已失效
 *  应使用元数据区限制：-XX:MaxMetaspaceSize=10m -XX:MetaspaceSize=10m
 */
public class TestMethodAreaOOM {
    public static void main(String[] args) {
        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                    return proxy.invokeSuper(obj, args);
                }
            });
            enhancer.create();
        }
    }

    static class OOMObject{
    }

    @Test
    void testStaticObject(){
        System.out.println(MetaSpaceObject.bytes);
    }

    static class MetaSpaceObject{
        /**
         * 这个静态变量存储在云数据区
         */
        private static byte[] bytes = new byte[1024 * 1024 * 5];
    }
}
