package net.tomcat.request;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author liqiang
 * @description
 * @date 2020/9/27 上午7:40
 */
public class LlServletRequest implements ServletRequest {

    private String method;

    private String url;

    /**
     * BIO解析 todo
     * @param inputStream
     */
    public LlServletRequest(InputStream inputStream){
        new BufferedReader(new InputStreamReader(inputStream))
                .lines()
                .limit(1)
                .forEach(line->{
                    String[] s = line.split(" ");
                    this.method = s[0];
                    this.url = s[1];
                });
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
