import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestBankClass {

    Bank bank;
    Account clientOne;
    Account clientTwo;
    Account clientThree;

    @Before
    public void setUp() {
        bank = new Bank();
        clientOne = new Account("4095641211", 1_500_000);
        clientTwo = new Account("40954121313", 2_000_000);
        clientThree = new Account("4095423213", 2_500_000);

        bank.addAccountToBank(clientOne);
        bank.addAccountToBank(clientTwo);
        bank.addAccountToBank(clientThree);
    }

    @Test
    public void testAllSumMoneyAccounts() {
        long expected = clientOne.getMoney() + clientTwo.getMoney() + clientThree.getMoney();
        long actual = bank.getSumAllAccounts();
        Assert.assertEquals(expected, actual);
    }
}
