package top.itlq.java.net.tomcat.response;

import lombok.Data;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.Channel;

/**
 * @author liqiang
 * @description
 * @date 2020/9/27 上午7:40
 */
public class LlServletResponse implements ServletResponse{

    private OutputStream outputStream;

    /**
     * BIO响应
     * @param outputStream
     */
    public LlServletResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void write(byte[] data) {
        try {
            //
            String response = "HTTP/1.1 200 OK\n" +
                    "Content-Type:text/plain;\n" +
                    "\r\n" +
                    new String(data);
            outputStream.write(response.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
