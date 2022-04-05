package jvm.invokevirtual;

/**
 * @author liqiang
 * @description
 * @date 2022/3/20 17:50
 */
public class Test {
    public static void main(String[] args) {
        Parent p = new Child();
        p.f1();

        Child c = new Child();
        c.f1();
    }
}

class Parent {
    public int f1() {
        f2();
        return 'P';
    }
    private int f2() {
        f1();
        return 'P';
    }
}

class Child extends Parent{
    @Override
    public int f1() {
        f2();
        return 'C';
    }
    private int f2() {
        return 'P';
    }
}