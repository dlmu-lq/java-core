package net.tomcat;

import net.tomcat.request.ServletRequest;
import net.tomcat.response.ServletResponse;

/**
 * @author liqiang
 * @description
 * @date 2020/9/27 上午8:12
 */
public class Test2Servlet implements Servlet {
    @Override
    public void doService(ServletRequest servletRequest, ServletResponse servletResponse) {
        servletResponse.write("test2".getBytes());
    }
}
