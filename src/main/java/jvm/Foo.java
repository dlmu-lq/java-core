package jvm;

/**
 * 获取字节码jasm格式 java -jar  C:\Users\liqiang\.m2\repository\org\openjdk\asmtools\asmtools-core\7.0.b10-ea\asmtools-core-7.0.b10-ea.jar jdis top\itlq\java\jvm\Foo.clas
 * s > Foo.jasm
 *
 * 修改字节码后
 * 反编译为class文件
 * java -jar  C:\Users\liqiang\.m2\repository\org\openjdk\asmtools\asmtools-core\7.0.b10-ea\asmtools-core-7.0.b10-ea.jar jasm Foo.jasm
 */
public class Foo {
  public static void main(String[] args) {
    boolean 吃过饭没 = true; // 直接编译的话 javac 会报错
    if (吃过饭没) System.out.println(" 吃了 ");
    if (true == 吃过饭没) System.out.println(" 真吃了 ");
  }
}