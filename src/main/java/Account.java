import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {
    private long money;
    private String accNumber;
    private boolean isBlocked;

    public Account(String accNumber, long money) {
        this.accNumber = accNumber;
        this.money = money;
        this.isBlocked = false;
    }

    public synchronized long getMoney() {
        return money;
    }

    public boolean isEnoughMoney(long amount) {
        if (money - amount >= 0) {
            money -= amount;
            return true;
        }
        return false;
    }

    public void receiveMoney(long amount) {
        money += amount;
    }
}
