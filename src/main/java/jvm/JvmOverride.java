package jvm;

public class JvmOverride {
}
//interface Customer {
//    boolean isVIP();
//}
//
//class Merchant {
//    public Number actionPrice(double price, Customer customer) {
//        return 1;
//    }
//}
//
//class NaiveMerchant extends Merchant {
//    @Override
//    public Double actionPrice(double price, Customer customer) {
//        return 1D;
//    }
//}
interface Customer {
    boolean isVIP();
}

class VIP implements Customer {

    @Override
    public boolean isVIP() {
        return true;
    }
}

class Merchant<T extends Customer> {
    public double actionPrice(double price, T customer) {
        return 1D;
    }
}

class VIPOnlyMerchant extends Merchant<VIP> {
    @Override
    public double actionPrice(double price, VIP customer) {
        return 1D;
    }
}

