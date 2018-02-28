/**
 * A class that is the referee for the Chips game, making sure that
 * the game is played properly with each player getting its own turn
 * 
 * @author Jack Hughes
 * @version 03/31/17
 */
public class ChipsReferee {
	
	private boolean verbosity;
	
	/**
	 * The constructor for the ChipsReferee, it sets the verbosity 
	 * to be true by default
	 */
	public ChipsReferee(){
		verbosity = false;
	}
	
	/**
	 * The getter for verbosity
	 * @return returns true or false
	 */
	public boolean getVerbosity(){
		return verbosity;
	}
	
	/**
	 * A setter for verbosity
	 * @param sets verbosity to the given parameter
	 */
	public void setVerbose(boolean b){
		verbosity = b;
	}
	
	/**
	 * A method that is the actual game referee for the class
	 * @param roster is an array of Players
	 * @param pilesize is the max size of the chips pile
	 * @return returns the name of the player that has won the game
	 */
	public String playGame(Player[] roster, int pilesize){
		Game g = new Game(pilesize);
		int count = 0;
		int numPlayers = roster.length;
		while(g.getPile() > 0){
			int move = roster[count].getMove(g);
			if(verbosity == true){
			System.out.println(roster[count].getName() +" removing " + move + 
					" chips from a pile of size " + g.getPile());
			}
			count++;
			if(count == numPlayers){
				count = 0;
			}
			g.removeChips(move);
		}
		if(count == 0){
			return roster[roster.length -1].getName();
		}
		else{
			return roster[count -1].getName();
		}
	}
}
