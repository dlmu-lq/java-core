package base.annotations_20.spring_mvc;

import java.lang.annotation.*;

/**
 * @author liqiang
 * @description
 * @date 2022/4/4 11:47
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ComponentScan {
    String[] packages();
}
