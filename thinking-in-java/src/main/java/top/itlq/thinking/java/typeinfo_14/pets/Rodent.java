/**
 * 啮齿动物
 */
package top.itlq.thinking.java.typeinfo_14.pets;

public class Rodent extends Pet {
    public Rodent(String name){super(name);}
    public static class Factory implements top.itlq.thinking.java.typeinfo_14.factory.Factory<Rodent> {
        @Override
        public Rodent create() {
            return new Rodent("factoryRodent");
        }
    }
}
