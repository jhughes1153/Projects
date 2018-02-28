import java.util.Random;

/**
 * A simple program to play a bunch of chips games based on a random number between
 * 3 and 2000
 * 
 * @author Jack Hughes
 * @version 03/31/17
 *
 */
public class ChipsMain {

    public static void main(String[] args) {
        ChipsReferee ref = new ChipsReferee();
        Random rand = new Random();
        ref.setVerbose(true);
        int a = rand.nextInt(2000) + 3;
        Player[] roster = {new JackPlayer(), new MaxPlayer()};
        String winner = ref.playGame(roster, a);
        System.out.println("The winner is player " + winner + ".");

    }

}
