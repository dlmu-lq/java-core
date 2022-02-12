package base.typeinfo_14.pets;

public class Pet extends Individual{
    public Pet(String name){super(name);}
    public static class Factory implements base.typeinfo_14.factory.Factory<Pet> {
        @Override
        public Pet create() {
            return new Pet("factoryPet");
        }
    }
}
