package top.itlq.thinking.java.typeinfo_14.pets;

public class Individual {
    public String name;

    public Individual(String name){
        this.name = name;
    }
    public static class Factory implements top.itlq.thinking.java.typeinfo_14.factory.Factory<Individual> {
        @Override
        public Individual create() {
            return new Individual("factoryIndividual");
        }
    }
}
