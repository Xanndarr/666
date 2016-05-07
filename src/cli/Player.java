package cli;

public class Player extends Bank {
	
	public Player() {
		super();
	}
	
	public Player(int money) {
		super(money);
	}
		
	public int placeBet() {
		int amount = (int) (Math.random() * 10) + 1;
		if(amount >= money){
			amount = money;
		}
		money -= amount;
		return amount;
	}
	
	public boolean reRoll(){
		int bool = (int) Math.round(Math.random());
		return bool == 1;
	}
}
