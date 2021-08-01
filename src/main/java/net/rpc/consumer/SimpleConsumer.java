package net.rpc.consumer;

import net.rpc.api.IComputeRpcService;

/**
 * @author liqiang04
 * @description TODO
 * @date 2020/9/27 8:32 下午
 */
public class SimpleConsumer {


    public static void main(String[] args) {
        IComputeRpcService computeRpcService = IntefaceProxy.getProxy(IComputeRpcService.class);
        Integer result = computeRpcService.add(1, 1);
        System.out.println(result);
    }
}
