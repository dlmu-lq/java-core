package top.itlq.java.test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestStream {
    public static void main(String[] args) {
        Foo f1 = new Foo("l", 1, 2),
        f2 = new Foo("l", 1, 2),
                f3 = new Foo("l", 1, 2),
                f4 = new Foo("c", 1, 2),
                f5 = new Foo("c", 1, 2),
                f6 = new Foo("q", 1, 2),
                f7 = new Foo("q", 1, 2);
        System.out.println(Stream.of(f1, f2, f3, f4, f5, f6, f7)
                .collect(Collectors.groupingBy(
                        Foo::getName,
                        Collectors.reducing(new Foo(null, 0, 0), (o, p) -> {
                            if (o.getName() == null) {
                                o.setName(p.getName());
                            }
                            o.setM(o.getM() + p.getM());
                            o.setN(o.getN() + p.getN());
                            return o;
                        })
                )));
    }
}

class Foo{
    private String name;
    private Integer m;
    private Integer n;
    private Integer count;

    public Foo(String name, Integer m, Integer n) {
        this.name = name;
        this.m = m;
        this.n = n;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getM() {
        return m;
    }

    public void setM(Integer m) {
        this.m = m;
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Foo{" +
                "name='" + name + '\'' +
                ", m=" + m +
                ", n=" + n +
                ", count=" + count +
                '}';
    }
}