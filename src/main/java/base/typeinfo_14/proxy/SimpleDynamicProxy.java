/**
 * 动态代理
 *
 */
package base.typeinfo_14.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

class DynamicProxyHandler implements InvocationHandler{

    private final Object proxied;
    public final Map<String,Integer> methodsCount = new HashMap<>();

    public DynamicProxyHandler(Object proxied){
        this.proxied = proxied;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        System.out.print(proxy); // proxy为外部创建的代理对象
//        System.out.print(proxied);
        System.out.println("proxy start");
        System.out.println("real start");
        methodsCount.put(method.getName(),methodsCount.getOrDefault(method.getName(),0) + 1);
        Object re = method.invoke(proxied,args);
        System.out.println("real end");
        System.out.println("proxy end");
        return re;
    }

    public Integer getMethodCount(String methodName){
        return methodsCount.getOrDefault(methodName, 0);
    }
}

public class SimpleDynamicProxy {

    static void consume(Interface obj){
        obj.doSomething();
//        System.out.println(obj);
    }

    public static void main(String [] args) {
        Interface realObj = new RealObject();
        consume(realObj);
        InvocationHandler handler = new DynamicProxyHandler(realObj);
        Interface proxy = (Interface) Proxy.newProxyInstance(
                Interface.class.getClassLoader(),
                new Class[]{Interface.class},
                handler);
//        System.out.println(proxy);
        consume(proxy);
        consume(proxy);
        for (Method method : realObj.getClass().getMethods())
            System.out.println(method.getName() + ":" + ((DynamicProxyHandler) handler).getMethodCount(method.getName()));

    }
}
