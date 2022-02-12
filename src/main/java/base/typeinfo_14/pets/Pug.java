package base.typeinfo_14.pets;

public class Pug extends Dog{
    public Pug(String name){super(name);}
    public static class Factory implements base.typeinfo_14.factory.Factory<Pug> {
        @Override
        public Pug create() {
            return new Pug("factoryPug");
        }
    }
}
