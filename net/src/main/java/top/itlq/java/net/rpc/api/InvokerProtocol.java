package top.itlq.java.net.rpc.api;

import lombok.Data;

/**
 * @author liqiang
 * @description
 * @date 2020/9/22 上午8:35
 */
@Data
public class InvokerProtocol {
    private Class<?> clazz;
    private String methodName;
    private Class<?>[] paramTypes;
    private Object[] paramValues;
}
