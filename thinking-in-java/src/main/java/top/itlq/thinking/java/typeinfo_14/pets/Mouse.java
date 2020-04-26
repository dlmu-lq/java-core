/**
 *
 */
package top.itlq.thinking.java.typeinfo_14.pets;

public class Mouse extends Rodent {
    public Mouse(String name){super(name);}
    public static class Factory implements top.itlq.thinking.java.typeinfo_14.factory.Factory<Mouse> {
        @Override
        public Mouse create() {
            return new Mouse("factoryMouse");
        }
    }
}
