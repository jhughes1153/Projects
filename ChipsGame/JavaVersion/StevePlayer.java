
public class StevePlayer implements Player {

	private String name = "Steve";

	@Override
	public int getMove(Game g) {
		if(g.getLimitMove() >= g.getPile()){
			return g.getPile();
		}
		else if(g.getPile() <= 20 && g.getPile()%2==0){
			return 1;
		}
		else if(g.getPile() == g.getLimitMove() + 1){
			return 1;
		}
		else return 2;
	}

	@Override
	public String getName() {
		return name;
	}
}