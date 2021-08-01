package net.rpc.api;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liqiang
 * @description
 * @date 2020/9/22 上午8:35
 */
@Data
public class InvokerProtocol implements Serializable {

    // 刚开始忘加
    private static final long serialVersionUID = -3386483233329606397L;

    private String clazzName;
    private String methodName;
    private Class<?>[] paramTypes;
    private Object[] paramValues;
}
