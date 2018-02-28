/**
 * A class that is the game of chips, it has the ability to set the
 * limit that a player may remove chips, and it actually removes chips
 * from the pile
 * 
 * @author Jack Hughes
 * @version 03/31/17
 */
public class Game {

	private int pile;
	private int limitMove;

	/**
	 * The constructor for the Game
	 * @param size is the size of the pile
	 */
	public Game(int size) {
		if (size < 3) {
			throw new IllegalArgumentException();
		} else {
			pile = size;
			limitMove = pile - 1;
		}
	}

	/**
	 * This method allows players to remove chips from the pile as long
	 * as it is not greater than the allowed amount, and also sets the
	 * next limitMove 
	 * @param remove the amount of chips to remove
	 */
	public void removeChips(int remove) {
		if (remove > limitMove) {
			throw new IllegalArgumentException();
		} else {
			pile = pile - remove;
			if(remove > limitMove){
				limitMove = pile -1;
			}
			else{
			limitMove = 2 * remove;
			}
		}
	}

	/**
	 * The getter for the pile variable
	 * @return returns pile
	 */
	public int getPile() {
		return pile;
	}

	/**
	 * The getter for the limit of the move that a player can make
	 * @return
	 */
	public int getLimitMove() {
		return limitMove;
	}

}
