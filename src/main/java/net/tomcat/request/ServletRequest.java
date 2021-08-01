package net.tomcat.request;

/**
 * @author liqiang
 * @description
 * @date 2020/9/27 上午7:45
 */
public interface ServletRequest {

    /**
     *
     * @return
     */
    String getMethod();

    /**
     *
     * @return
     */
    String getUrl();
}
