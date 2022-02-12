package base.typeinfo_14.pets;

public class Cat extends Pet {
    public Cat(String name){super(name);}
    public static class Factory implements base.typeinfo_14.factory.Factory<Cat> {
        @Override
        public Cat create() {
            return new Cat("factoryCat");
        }
    }
}
