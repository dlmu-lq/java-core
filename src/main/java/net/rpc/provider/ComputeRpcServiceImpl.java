package net.rpc.provider;

import net.rpc.api.IComputeRpcService;

/**
 * @author liqiang
 * @description
 * @date 2020/9/22 上午8:36
 */
public class ComputeRpcServiceImpl implements IComputeRpcService {

    @Override
    public Integer add(Integer a, Integer b) {
        return a + b;
    }
}
