package net.tomcat.request;

import io.netty.handler.codec.http.HttpRequest;

/**
 * @author liqiang
 * @description
 * @date 2020/9/27 上午7:40
 */
public class NtServletRequest implements ServletRequest {

    private String method;

    private String url;

    /**
     * netty 解析完成的
     * @param httpRequest
     */
    public NtServletRequest(HttpRequest httpRequest){
        this.method = httpRequest.method().name();
        this.url = httpRequest.uri();
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getUrl() {
        return url;
    }
}
