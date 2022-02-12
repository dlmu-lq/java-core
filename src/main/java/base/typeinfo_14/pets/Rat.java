package base.typeinfo_14.pets;

public class Rat extends Rodent {
    public Rat(String name){super(name);}
    public static class Factory implements base.typeinfo_14.factory.Factory<Rat> {
        @Override
        public Rat create() {
            return new Rat("factoryRat");
        }
    }
}
