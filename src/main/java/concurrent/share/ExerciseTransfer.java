package concurrent.share;

import java.util.Random;

public class ExerciseTransfer {
    public static void main(String[] args) {
        Account account1 = new Account(1000);
        Account account2 = new Account(1000);
        Thread t1 = new Thread(()->{
            Random random = new Random();
            for(int i=0;i<10000;i++){
//                account1.transfer(account2, random.nextInt(10));
//                account1.transfer2(account2, random.nextInt(10));
                account1.transfer3(account2, random.nextInt(10));
            }
        }, "t1");

        Thread t2 = new Thread(()->{
            Random random = new Random();
            for(int i=0;i<10000;i++){
//                account2.transfer(account1, random.nextInt(10));
//                account2.transfer2(account1, random.nextInt(10));
                account2.transfer3(account1, random.nextInt(10));
            }
        }, "t2");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(account1.getAmount() + account2.getAmount() != 2000){
            System.out.println("线程不安全，转账失败");
        }else{
            System.out.println("线程安全，转账成功");
        }
    }
}

class Account{
    private int amount;

    public Account(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    // 此处要锁定两个账户
    public void transfer(Account account, int transferAmount){
        if(amount > transferAmount){
            amount -= transferAmount;
            account.amount += transferAmount;
        }
    }

    public synchronized void transfer2(Account account, int transferAmount){
        if(amount > transferAmount){
            amount -= transferAmount;
            account.amount += transferAmount;
        }
    }

    public void transfer3(Account account, int transferAmount){
        synchronized (Account.class){
            if(amount > transferAmount){
                amount -= transferAmount;
                account.amount += transferAmount;
            }
        }
    }
}