/**
 * A class that is a player that always plays the max value
 * 
 * @author Jack Hughes
 * @version 03/31/17
 */
public class MaxPlayer implements Player {

	private String name;
	
	/**
	 * A constructor that sets the name
	 * @param naming the name to give the player
	 */
	public MaxPlayer(String naming){
		name = naming;
	}
	
	/**
	 * Defaults the name to maxplaye if no paramter is given
	 */
	public MaxPlayer(){
		name = "MaxPlayer";
	}
	/**
	 * gets the move based on the highest number that it can play
	 */
	@Override
	public int getMove(Game g) {
		if(g.getLimitMove() > g.getPile()){
			return g.getPile();
		}
		else{
		return (g.getLimitMove());
		}
	}

	/**
	 * Returns the name of the player
	 */
	@Override
	public String getName() {
		return name;
	}

}
