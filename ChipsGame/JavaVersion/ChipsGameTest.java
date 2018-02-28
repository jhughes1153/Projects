import static org.junit.Assert.*;

import org.junit.Test;

/**
 * A class that is used to test the Game class
 * 
 * @author Jack Hughes
 * @version 03/31/17
 */
public class ChipsGameTest {

	/**
	 * This test was to test that the getPile is working properly when chips are 
	 * removed 4 separate times
	 */
	@Test
	public void testRemoveBasic(){
		Game game = new Game(10);
		game.removeChips(1);
		System.out.println(game.getLimitMove() + " " + game.getPile());
		game.removeChips(1);
		System.out.println(game.getLimitMove() + " " + game.getPile());
		game.removeChips(1);
		System.out.println(game.getLimitMove() + " " + game.getPile());
		game.removeChips(1);
		System.out.println(game.getLimitMove() + " " + game.getPile());
		assertEquals("Bad Remove Method", 6, game.getPile(),0.001);
	}
	
	/**
	 * Tests that an exception is thrown when someone tries to remove more chips than
	 * are in the pile
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testRemoveMoreThanPile(){
		Game game = new Game(100);
		game.removeChips(1000);
	}
	
	/**
	 * Tests that an exception is thrown when someone tries to create a game with a pile
	 * less than 3
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testGameLessThanThree(){
		@SuppressWarnings("unused")
		Game game = new Game(2);
	}

	/**
	 * Tests that the getPile method works right when the game is started
	 */
	@Test
	public void testGetPile(){
		Game game = new Game(10);
		assertEquals("Bad Remove Method", 10, game.getPile(),0.001);
	}
	
	/**
	 * Tests that the removeChips method is working right for the initial remove
	 */
	@Test
	public void testLimitMove(){
		Game game = new Game(10);
		game.removeChips(2);
		assertEquals("Bad Remove Method", 4, game.getLimitMove(),0.001);
	}
	
	/**
	 * Tests that the getLimitMove method is working right
	 */
	@Test
	public void testLimitMove2(){
		Game game = new Game(10);
		game.removeChips(4);
		assertEquals("Bad Remove Method", 8, game.getLimitMove(),0.001);
	}
	
	/**
	 * Tests that an exception is thrown if a player tries to remove more chips 
	 * than the allowed amount
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testLimitGreaterThan(){
		Game game = new Game(10);
		game.removeChips(2);
		game.removeChips(8);
	}
}
