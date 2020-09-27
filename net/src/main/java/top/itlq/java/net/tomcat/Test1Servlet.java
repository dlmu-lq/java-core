package top.itlq.java.net.tomcat;

import top.itlq.java.net.tomcat.request.ServletRequest;
import top.itlq.java.net.tomcat.response.ServletResponse;

/**
 * @author liqiang
 * @description
 * @date 2020/9/27 上午8:12
 */
public class Test1Servlet implements Servlet{
    @Override
    public void doService(ServletRequest servletRequest, ServletResponse servletResponse) {
        servletResponse.write("test1".getBytes());
    }
}
