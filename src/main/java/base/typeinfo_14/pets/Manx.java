package base.typeinfo_14.pets;

public class Manx extends Pet {
    public Manx(String name){super(name);}

    public static class Factory implements base.typeinfo_14.factory.Factory<Manx> {
        @Override
        public Manx create() {
            return new Manx("factoryManx");
        }
    }
}
