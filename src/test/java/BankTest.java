import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BankTest extends TestCase {
    Bank bank;
    Map<String, Account> accounts = new HashMap<>();

    @Override
    protected void setUp() {
        for (int i = 1; i <= 5; i++) {
            Account account = new Account(String.valueOf(i), i * 15000);
            accounts.put(account.getAccNumber(), account);
        }
        bank = new Bank(accounts);
    }

    public void testOneThreadTransfer() throws InterruptedException {
        bank.transfer("1", "2", 10000);

        long senderExpected = 5000;
        long senderActual = bank.getBalance("1");
        assertEquals(senderExpected, senderActual);

        long receiverExpected = 40000;
        long receiverActual = bank.getBalance("2");
        assertEquals(receiverExpected, receiverActual);
    }

    public void testManyThreadsTransfer() {
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            threads.add(new Thread(() -> {
                for (int j = 1; j <= 4; j++) {
                    String fromAccountNum = String.valueOf(j);
                    String toAccountNum = String.valueOf(j + 1);
                    long amount = 5000;
                    try {
                        bank.transfer(fromAccountNum, toAccountNum, amount);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }));
        }
        threads.forEach(Thread::run);

        long firstExpected = 0;
        long firstActual = accounts.get("1").getMoney();
        assertEquals(firstExpected, firstActual);

        long thirdExpected = 45000;
        long thirdActual = bank.getBalance("3");
        assertEquals(thirdExpected, thirdActual);

        long fifthExpected = 90000;
        long fifthActual = bank.getBalance("5");
        assertEquals(fifthExpected, fifthActual);
    }

    public void testSumAllAccounts() {
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            threads.add(new Thread(() -> {
                for (int j = 1; j <= 4; j++) {
                    String fromAccountNum = String.valueOf(j);
                    String toAccountNum = String.valueOf(j + 1);
                    long amount = 3000;
                    try {
                        bank.transfer(fromAccountNum,toAccountNum, amount);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }));
        }
        threads.forEach(Thread::run);

        long expectedSum = 225000;
        long actualSum = bank.getSumAllAccounts();

        assertEquals(expectedSum, actualSum);
    }



}
