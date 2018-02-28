/**
 * A class that always plays 1 when playing the game
 * 
 * @author jhughes
 * @version 03/31/17
 */
public class OnesPlayer implements Player{
	
	private String name;
	
	/**
	 * Parameterless constructor that automatically sets the name
	 */
	public OnesPlayer(){
		name = "OnesPlayer";
	}
	
	/**
	 * The constructor for the OnesPlayer
	 * @param naming names the player
	 */
	public OnesPlayer(String naming){
		name = naming;
	}

	/**
	 * always returns 1 for its move
	 */
	@Override
	public int getMove(Game g) {
		return 1;
	}

	/**
	 * gets the name of the player
	 */
	@Override
	public String getName() {
		return name;
	}

	
}
