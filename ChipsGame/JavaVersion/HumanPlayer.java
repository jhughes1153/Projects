import java.util.Scanner;
/**
 * A class that allows a person to put inputs into the console to play the game
 * 
 * @author Jack Hughes
 * @version 03/31/17
 */
public class HumanPlayer implements Player{

	private String name;

	/**
	 * The constructor for the class, so it sets the name based on what 
	 * the user puts in the console
	 */
	public HumanPlayer(){
		System.out.println("Please enter your name");
		Scanner console = new Scanner(System.in);
		name = console.nextLine();
	}

	/**
	 * Take the output that the user puts in and makes the move based on that, doesn't
	 * let the use put in a number that is greater than the limit of the move
	 */
	@Override
	public int getMove(Game g) {
		System.out.println("Please enter a number between 1 and " + g.getLimitMove());
		Scanner scan = new Scanner(System.in);
		int temp = scan.nextInt();
		if(temp > g.getLimitMove()){
			System.out.println("Dont be a cheater");
			return g.getLimitMove();
		}else{
		return temp;
		}
	}

	/**
	 * A getter for the name 
	 */
	@Override
	public String getName() {
		return name;
	}

}
