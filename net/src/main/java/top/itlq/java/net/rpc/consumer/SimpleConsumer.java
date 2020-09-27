package top.itlq.java.net.rpc.consumer;

import top.itlq.java.net.rpc.api.IComputeRpcService;

/**
 * @author liqiang04
 * @description TODO
 * @date 2020/9/27 8:32 下午
 */
public class SimpleConsumer {

    public static IComputeRpcService computeRpcService = ObjectProxy.getProxy(IComputeRpcService.class);

    public static void main(String[] args) {
        Integer result = computeRpcService.add(1, 2);
        System.out.println(result);
    }
}
