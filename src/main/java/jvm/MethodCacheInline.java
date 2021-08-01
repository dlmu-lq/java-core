package jvm;

public class MethodCacheInline {
    public static void main(String[] args) {
        Passenger a = new ChinesePassenger();
        Passenger b = new ForeignerPassenger();
        long current = System.currentTimeMillis();
        for (int i = 1; i <= 2_000_000_000; i++) {
            if (i % 100_000_000 == 0) {
                long temp = System.currentTimeMillis();
                System.out.println(temp - current);
                current = temp;
            }
            Passenger c = (i < 1_000_000_000) ? a : b;
            c.passThroughImmigration();
        }
    }
}
// Run with: java -XX:CompileCommand='dontinline,*.passThroughImmigration' Passenger
abstract class Passenger {
    abstract void passThroughImmigration();
}
class ChinesePassenger extends Passenger {
    @Override void passThroughImmigration() {}
}
class ForeignerPassenger extends Passenger {
    @Override void passThroughImmigration() {}
}