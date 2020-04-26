package top.itlq.thinking.java.typeinfo_14.pets;

public class Mutt extends Dog{
    public Mutt(String name){super(name);}
    public static class Factory implements top.itlq.thinking.java.typeinfo_14.factory.Factory<Mutt> {
        @Override
        public Mutt create() {
            return new Mutt("factoryMutt");
        }
    }
}
