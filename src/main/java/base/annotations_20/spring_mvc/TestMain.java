package base.annotations_20.spring_mvc;

//import base.chapter09_polymorphism.FieldAccess;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/**
 * @author liqiang
 * @description
 * @date 2022/4/4 11:46
 */
public class TestMain {

    Set<String> classNames = new HashSet<>();

    Map<String, Object> container = new HashMap<>();

    Map<String, Method> urlHandlerMappings = new HashMap<>();

    /**
     * > 配置类/加载配置文件，获取包
     * > 包扫描
     * > 构建容器，创建bean
     * > DI，解析依赖关系
     * > 解析web相关注解，构建url -> handler映射关系
     * >
     */
    public void init() {
        SpringMvcConfig config = new SpringMvcConfig();

        // 配置类/加载配置文件，获取包
        ComponentScan annotation = config.getClass().getAnnotation(ComponentScan.class);
        String[] packages = annotation.packages();
        for (String packageOne: packages) {
             scanPackages(packageOne);
        }

        // 扫描包
    }

    public List<Class<?>> scanPackages(String packageOne) {
        List<Class<?>> list = new ArrayList<>();
        URL resource = TestMain.class.getClassLoader().getResource(packageOne.replaceAll("\\.", "/"));
        File[] files = new File(resource.getFile()).listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                list.addAll(scanPackages(file.getPath()));
            } else {
                Class<?> aClass = null;
                try {
                    aClass = Class.forName(packageOne + "." + file.getName());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                list.add(aClass);
            }
        }
        return list;
    }
}
