import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {
    private long money;
    private String accNumber;
    private boolean isBlocked = false;

    public synchronized long getMoney() {
        return money;
    }

    public boolean isEnoughMoney(long amount) {
        return money - amount >= 0;
    }

    public void receiveMoney(long amount) {
        money += amount;
    }
}
