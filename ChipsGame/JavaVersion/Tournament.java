import java.util.ArrayList;

public class Tournament {

	private static ArrayList<String> returner = new ArrayList<String>();
	private static int total;
	public static void main(String[] args) {
		Player[] play = { new OnesPlayer("Onesy"), new MaxPlayer("Greedy"), new MaxPlayer("Selfish"),
				new RandomPlayer("Lucky") };
		int[] pilestuff = { 10, 21, 55, 117 };
		runTourney(play, pilestuff);

	}

	public static ArrayList<String> runTourney(Player[] players, int[] pileSize) {
		int count = 0;
		int countA = 1;
		int countUp = 0;
		for (int i = (players.length - 1); i > 0; i--) {
			total = total + i;
		}

		for (int i = 0; i < total; i++) {
			String a = playMatch(players, count, countA, countUp, pileSize);
			// System.out.println(players[count].getName() + " plays " +
			// players[countA].getName());
			if (countA == players.length - 1) {
				countA = count + 1;
				count++;
			}

			countA++;
			if (countUp == pileSize.length - 1) {
				countUp = 0;
			} else {
				countUp++;
			}
			returner.add(a);
		}
		return returner;
	}

	private static String playMatch(Player[] players, int count, int countA, int countUp, int[] pileSize) {
		int winsA = 0;
		int winsB = 0;

		for (int i = 0; i < pileSize.length; i++) {
			Player[] roster = { players[count], players[countA] };
			Player[] rosterA = { players[countA], players[count] };
			ChipsReferee ref = new ChipsReferee();
			String winner = ref.playGame(roster, pileSize[countUp]);
			String winnerA = ref.playGame(rosterA, pileSize[countUp]);
//			System.out.println(players[count].getName() + " plays " + players[countA].getName() + " with " + pileSize[i] + "chips");
//			System.out.println("The winner is: " + winner);
//			System.out.println(players[countA].getName() + " plays " + players[count].getName() + " with " + pileSize[i] + "chips");
//			System.out.println("The winner is: " + winnerA);
			if(winner.equals(players[count].getName()) && winnerA.equals(players[count].getName())){
				winsA = winsA + 2;
			}
			else if(winner.equals(players[countA].getName()) && winnerA.equals(players[countA].getName())){
				winsB = winsB + 2;
			}
			else{
				winsA++;
				winsB++;
			}
		}
		System.out.println(players[count].getName() + " " + winsA + " " + players[countA].getName() + " " + winsB);
		return players[count].getName() + " " + winsA + ", " + players[countA].getName() + " " + winsB;
	}
	
	public static int getTotal(){
		return total;
	}

}

