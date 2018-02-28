/**
 * An interface for Players
 * 
 * @author jhughes
 * @version 03/31/17
 */
public interface Player {

	/**
	 * All players must be able to make a move
	 * @param g is the game that is being played
	 */
	public int getMove(Game g);
	
	/**
	 * Gets the name of the player so we can differentiate them
	 */
	public String getName();
	
}
