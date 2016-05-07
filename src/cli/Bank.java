package cli;

public class Bank {
	int money = 0;
	
	public Bank() {
		money = 2000;
	}
	
	public Bank(int money) {
		this.money = money;
	}
	
	public int getMoney() {
		return money;
	}
	
	public void addWinnings(int amount) {
		money += amount;
	}
}
