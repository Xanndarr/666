package cli;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
	
	private final int bronzePayout = 1;
	private final int silverPayout = 43;
	private final int goldPayout = 100;

	private ArrayList<Player> players = new ArrayList<Player>();
	private Dealer dealer = null;
	
	private Dice dice = null;
	
	public static void main(String[] args) {
		Main table = new Main();
		try{
			table.setupTable(Integer.parseInt(args[0]));
			table.run(Boolean.parseBoolean(args[1]), Integer.parseInt(args[2]));
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("Usage: java -jar 666.jar {initial players} {show table state} {number of hands}");
		}
	}
	
	public void setupTable(int numberOfPlayers){
		dice = new Dice();
		
		for(int i = 0; i < numberOfPlayers; i++){
			players.add(new Player());
		}
		dealer = new Dealer(10000);
	}
	
	public void run(boolean showOutput, int numberOfHands){
		for(int i = 0; i < numberOfHands; i++){
			playRound();
			if(showOutput){
				printBalances();
			}
		}
		System.out.println("FINAL BALANCES: ");
		printBalances();
	}
	
	private void playRound(){
		HashMap<Player, Integer> scores = new HashMap<Player, Integer>();
		HashMap<Player, Integer> bets = new HashMap<Player, Integer>();
		ArrayList<Player> winners = new ArrayList<Player>();
		
		for(Player p : players){
			if(p.getMoney() > 0){
				int bet = p.placeBet();
				bets.put(p, bet);
				int roll = dice.roll();
				scores.put(p, roll);
				System.out.print(roll + ":" + bet + "\t\t");
			}else{
				System.out.print("N:A\t\t");
			}
		}
		int dealersScore = dice.roll();
		System.out.println("===>" + dealersScore);
		
		for(Player p : scores.keySet()){
			int score = scores.get(p);
			int bet = bets.get(p);
			if(score == dealersScore){
				payout(p, bet);
			}else if(score > dealersScore){
				if(score == 6){
					winners.add(p);
				}else{
					payout(p, bet * (1 + bronzePayout));
				}
			}
		}
		
		for(Player p : winners){
			int bet = bets.get(p);
			if(p.reRoll()){
				if(dice.roll() == 6){
					if(p.reRoll()){
						if (dice.roll() == 6) {
							payout(p, bet * (1 + goldPayout));
						}
					}else{
						payout(p, bet * (1 + silverPayout));
					}
				}
			}else{
				payout(p, bet * (1 + bronzePayout));
			}
		}
		
		for(Integer bet : bets.values()){
			dealer.addWinnings(bet);
		}
	}
	
	private void printBalances(){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < players.size(); i++){
			sb.append(players.get(i).getMoney() + "\t\t");
		}
		System.out.println("House : " + dealer.getMoney());
		System.out.println(sb.toString() + "\n");
	}
	
	private void payout(Player p, int amount){
		p.addWinnings(amount);
		dealer.payout(amount);
	}
	
}
