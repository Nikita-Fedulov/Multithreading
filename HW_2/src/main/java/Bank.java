import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Bank  {

    private static final ConcurrentMap<String, Account> accounts = new ConcurrentHashMap<>();
    private final Random random = new Random();
    long verificationLimit = 50_000;

    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
            throws InterruptedException {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    public void addAccountToBank(Account account) {
        accounts.put(account.getAccNumber(), account);
    }

    public  synchronized void getAccounts() {
        System.out.println(accounts.keySet());
    }

    public synchronized void transfer(String fromAccountNum, String toAccountNum, long amount) {
        Account fromAccount = accounts.get(fromAccountNum);
        Account toAccount = accounts.get(toAccountNum);
        boolean check = false;
        if (isEnoughMoney(fromAccount.getMoney(), amount)) {
            if (amount > verificationLimit) {
                try {
                    check = isFraud(fromAccountNum, toAccountNum, amount);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
                if (check == true) {
                    fromAccount.blockedAccount();
                    toAccount.blockedAccount();
                } else {
                    toAccount.setMoney(toAccount.getMoney() + amount);
                    fromAccount.setMoney(fromAccount.getMoney() - amount);
                }
            } else {
                toAccount.setMoney(toAccount.getMoney() + amount);
                fromAccount.setMoney(fromAccount.getMoney() - amount);
            }
        }
    }


    public synchronized long getBalance(String accountNum) {
        if (accountNum == null || accountNum.isEmpty() || !accounts.containsKey(accountNum)) {
            System.out.println("Аккаунт: " + accountNum + " не найден!");
        }
        return accounts.get(accountNum).getMoney();
    }


    public synchronized long getSumAllAccounts() {
        long sumMoney = 0;
        for (Account values : accounts.values()) {
            sumMoney = sumMoney + values.getMoney();
        }
        System.out.println("Общая сумма на всех счетах: ");
        return sumMoney;
    }

    public boolean isEnoughMoney(long fromAccountMoney, long amount) {
        return fromAccountMoney >= amount;
    }

}

