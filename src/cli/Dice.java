package cli;

public class Dice {
	private static final int faces = 6;
	
	public int roll(){
		return (int) (Math.random() * faces) + 1;
	}
	
}
