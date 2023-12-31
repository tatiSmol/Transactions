import java.util.Map;
import java.util.Random;

public class Bank {
    private final Map<String, Account> accounts;
    private final Random random = new Random();

    public Bank(Map<String, Account> accounts) {
        this.accounts = accounts;
    }

    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
        throws InterruptedException {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    public void transfer(String fromAccountNum, String toAccountNum, long amount) throws InterruptedException {
        synchronized (accounts) {
            Account sender = accounts.get(fromAccountNum);
            Account recipient = accounts.get(toAccountNum);

            if (sender == null || recipient == null || sender.isBlocked() || recipient.isBlocked()) {
                return;
            }
            if (amount > 50_000 && isFraud(fromAccountNum, toAccountNum, amount)) {
                sender.setBlocked(true);
                recipient.setBlocked(true);
            } else if (sender.isEnoughMoney(amount)) {
                recipient.receiveMoney(amount);
            }
        }
    }

    public long getBalance(String accountNum) {
        synchronized (accounts) {
            Account account = accounts.get(accountNum);
            return account == null ? 0 : account.getMoney();
        }
    }

    public long getSumAllAccounts() {
        synchronized (accounts) {
            return accounts.values().stream().mapToLong(Account::getMoney).sum();
        }
    }
}
