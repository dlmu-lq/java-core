package base.generics;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * @author liqiang
 * @description 三种方式获取泛型参数
 * @date 2022/2/12 10:27
 */
public class TestGetGeneric {

    private static Generic1<B> generic1;

    interface GenericInterface<T> {

    }

    /**
     * 根据类继承、接口实现，获取父类，父接口的泛型参数
     */
    static class ClazzGeneric implements GenericInterface<B> {

    }

    /**
     * 根据class定义字段获取泛型参数
     */
    static class FieldGeneric {
        Generic1<C> generic1;
    }
    /**
     * 根据class定义方法获取泛型参数
     */
    static class MethodGeneric {
        Generic1<B> generic1() {
            return null;
        }
    }

    public static void main(String[] args) {
        ClazzGeneric generic1 = new ClazzGeneric();
        // 获取实现的接口的泛型参数
        Type[] genericInterfaces = generic1.getClass().getGenericInterfaces();
        System.out.println(Arrays.toString(genericInterfaces));
        for (Type genericInterface : genericInterfaces) {
            System.out.println(Arrays.toString(((ParameterizedType)genericInterface).getActualTypeArguments()));
        }

        for (Field field : FieldGeneric.class.getDeclaredFields()) {
            ParameterizedType genericType = (ParameterizedType)field.getGenericType();
            System.out.println(genericType);
            System.out.println(Arrays.toString(genericType.getActualTypeArguments()));
        }

        for (Method method : MethodGeneric.class.getDeclaredMethods()) {
            ParameterizedType genericReturnType = (ParameterizedType) method.getGenericReturnType();
            System.out.println(genericReturnType);
            System.out.println(Arrays.toString(genericReturnType.getActualTypeArguments()));
        }
    }

}
