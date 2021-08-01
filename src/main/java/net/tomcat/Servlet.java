package net.tomcat;

import net.tomcat.request.ServletRequest;
import net.tomcat.response.ServletResponse;

/**
 * @author liqiang
 * @description
 * @date 2020/9/27 上午7:39
 */
public interface Servlet {
    /**
     * 容器封装后暴露给业务层
     * @param servletRequest
     * @param servletResponse
     */
    void doService(ServletRequest servletRequest, ServletResponse servletResponse);
}
