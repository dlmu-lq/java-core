package net.tomcat.response;

/**
 * @author liqiang
 * @description
 * @date 2020/9/27 上午7:47
 */
public interface ServletResponse {
    /**
     * 写入响应数据
     * @param data
     * @return
     */
    void write(byte[] data);
}
