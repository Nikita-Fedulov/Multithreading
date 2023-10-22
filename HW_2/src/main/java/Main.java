import java.sql.SQLOutput;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Bank bank = new Bank();

        Account firstClient1 = new Account("1", 100);
        Account secondClient1 = new Account("2", 1000000);
        Account firstClient2 = new Account("3", 5000);
        Account secondClient2 = new Account("4", 3300);

        bank.addAccountToBank(firstClient1);
        bank.addAccountToBank(secondClient1);
        bank.addAccountToBank(firstClient2);
        bank.addAccountToBank(secondClient2);

        System.out.println(bank.getSumAllAccounts());

        Thread threadOne =
                new Thread(
                        () -> {
                            bank.transfer(firstClient1.getAccNumber(), secondClient1.getAccNumber(), 100);
                            bank.transfer(secondClient1.getAccNumber(), firstClient1.getAccNumber(), 100);
                            System.out.println(Thread.currentThread().getName());
                        });

        Thread threadTwo =
                new Thread(
                        () -> {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            bank.transfer(secondClient1.getAccNumber(), firstClient1.getAccNumber(), 500000);
                            bank.transfer(firstClient1.getAccNumber(), secondClient1.getAccNumber(), 0);
                            System.out.println(Thread.currentThread().getName());
                        });

        threadOne.start();
        threadTwo.start();
        threadOne.join();
        threadTwo.join();
        System.out.println(bank.getSumAllAccounts());

    }
}
