/**
 * 仓鼠
 */
package top.itlq.thinking.java.typeinfo_14.pets;

public class Hamster extends Rodent {
    public Hamster(String name){super(name);}
    public static class Factory implements top.itlq.thinking.java.typeinfo_14.factory.Factory<Hamster> {
        @Override
        public Hamster create() {
            return new Hamster("factoryHamster");
        }
    }
}
