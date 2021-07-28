package top.itlq.java.test;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//import javax.management.ConstructorParameters;
import javax.xml.namespace.QName;
import java.beans.ConstructorProperties;
import java.util.stream.Collector;
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
                            o.setCount(o.getCount() == null ? 1 : o.getCount() + 1);
                            return o;
                        })
                ))
        );
        System.out.println(Stream.of(f1, f2, f3, f4, f5, f6, f7)
                .collect(Collectors.groupingBy(
                        Foo::getName,
                        Collector.of(() -> new Foo(null, 0, 0), (o, p) -> {
                            if (o.getName() == null) {
                                o.setName(p.getName());
                            }
                            o.setM(o.getM() + p.getM());
                            o.setN(o.getN() + p.getN());
                            o.setCount(o.getCount() == null ? 1 : o.getCount() + 1);
                        }, (o, p) -> o)
                ))
        );
    }
}

@Getter
@Setter
@ToString
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
}