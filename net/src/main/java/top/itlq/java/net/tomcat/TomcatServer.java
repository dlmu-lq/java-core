package top.itlq.java.net.tomcat;

import org.apache.logging.log4j.core.util.FileUtils;
import sun.nio.ch.IOUtil;
import top.itlq.java.net.tomcat.request.LlServletRequest;
import top.itlq.java.net.tomcat.response.LlServletResponse;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author liqiang
 * @description 简单的tomcat server
 * @date 2020/9/21 下午10:48
 */
public class TomcatServer {

    private static final String PROPERTIES_CLASSPATH = "web.properties";
    private static final Map<String, Servlet> URL_SERVLET = new HashMap<>();

    public static void main(String[] args) throws IOException {
        TomcatServer tomcatServer = new TomcatServer();
        tomcatServer.loadConfig();
        tomcatServer.startBioServer();
    }

    /**
     * 加载web.properties配置
     */
    private void loadConfig(){
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResource(PROPERTIES_CLASSPATH).openStream();
//            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
//            br.lines().forEach(line->{
//                String[] split = line.split("=");
//            });
            Properties properties = new Properties();
            properties.load(inputStream);
            properties.forEach((s1, s2)->{
                try {
                    Object o = Class.forName((String) s2).newInstance();
                    URL_SERVLET.put((String) s1, (Servlet) o);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startBioServer() throws IOException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 4, 60L, TimeUnit.SECONDS, new LinkedBlockingDeque<>(100));
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(8080));
        while (true){
            Socket socket = serverSocket.accept();
            threadPoolExecutor.execute(()->{
                try {
                    InputStream inputStream = socket.getInputStream();
                    OutputStream outputStream = socket.getOutputStream();

                    LlServletRequest servletRequest = new LlServletRequest(inputStream);
                    LlServletResponse servletResponse = new LlServletResponse(outputStream);
                    URL_SERVLET.get(servletRequest.getUrl()).doService(servletRequest, servletResponse);

                    outputStream.flush();
                    outputStream.close();

                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
