/**
 * 啮齿动物
 */
package base.typeinfo_14.pets;

public class Rodent extends Pet {
    public Rodent(String name){super(name);}
    public static class Factory implements base.typeinfo_14.factory.Factory<Rodent> {
        @Override
        public Rodent create() {
            return new Rodent("factoryRodent");
        }
    }
}
