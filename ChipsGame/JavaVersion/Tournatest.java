
public class Tournatest {

	public static void main(String[] args) {
		Player[] play = { new OnesPlayer("Onesy"), new MaxPlayer("Greedy"), new MaxPlayer("Selfish"),
				new RandomPlayer("Lucky") };
		int[] pilestuff = { 10, 21, 55, 117 };
		Tournament.runTourney(play, pilestuff);
	}

}
