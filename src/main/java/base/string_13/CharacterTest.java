package base.string_13;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author liqiang04
 * @description TODO
 * @date 2022/3/25 7:12 下午
 */
public class CharacterTest {

    public static void main(String[] args) {
        System.out.println(Arrays.toString("国".getBytes()));
        System.out.println(Arrays.toString("国".getBytes(StandardCharsets.UTF_16LE)));
        System.out.println(Arrays.toString("国".getBytes(StandardCharsets.UTF_16BE)));
        System.out.println(Arrays.toString("a".getBytes(StandardCharsets.UTF_16BE)));

        // UTF-16 字符串，会在前面加 BOM
        System.out.println(Arrays.toString("国".getBytes(StandardCharsets.UTF_16)));
        System.out.println(Arrays.toString("国a".getBytes(StandardCharsets.UTF_16)));

        System.out.println("国".charAt(0));

        char a = '国';

//        char b = '😂';

        System.out.println(Arrays.toString("😂".getBytes()));
        System.out.println(Arrays.toString("😂".getBytes(StandardCharsets.UTF_16BE)));

        // 特殊字符，表情，其实是用两个字符表示
        System.out.println("😂".charAt(1));

        System.out.println("国😂");

        System.out.println(Arrays.toString("org".getBytes(StandardCharsets.UTF_8)));
    }

}
