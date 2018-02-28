import java.util.Random;

/**
 * A class that implements the player interface, it is a player for the 
 * chips game that selects a random number based on the highest move that
 * it can make for that turn
 * 
 * @author Jack Hughes
 * @version 03/31/17
 */
public class RandomPlayer implements Player {

	private String name;
	
	/**
	 * A parameterless constructor for the RandomClass
	 */
	public RandomPlayer(){
		name = "RandomPlayer";
	}
	
	/**
	 * The constructor for the RandomPlayer object
	 * @param naming accepts a string for the name of the Player
	 */
	public RandomPlayer(String naming){
		name = naming;
	}
	
	/**
	 * Makes a move that is randomized by the highest move that 
	 * it can make
	 */
	@Override
	public int getMove(Game g) {
		if(g.getPile() > g.getLimitMove()){
		Random rand = new Random();
		int a = rand.nextInt(g.getLimitMove()) + 1;
		return a;
		}
		else {
			return g.getPile();
		}
	}

	/**
	 * the getter for the name of the Player
	 */
	@Override
	public String getName() {
		return name;
	}

}
