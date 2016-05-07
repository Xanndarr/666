package cli;

public class Dealer extends Bank {
	
	public Dealer(int money) {
		super(money);
	}
	
	public int payout(int amount){
		money -= amount;
		return amount;
	}
	
}
