/**
 * This is the class that will be used in the contest, it is about even
 * with a class that is similar to itself, but it beats me most of the time
 * 
 * @author Jack Hughes
 * @version 03/31/17
 */
public class JackPlayer implements Player {

	private String name = "Jack";

	/**
	 * Default constructor for the JackPlayer, sets name to Jack
	 */
	public JackPlayer(){
		name = "Jack";
	}
	
	/**
	 * Sets JackPlayer to a different name besides Jack
	 */
	public JackPlayer(String namer){
		name = namer;
	}
	
	/**
	 * This method is smarter than the other Players we have made so far
	 * but is right about even with a class that is slightly different, 
	 * it likes to start with positive numbers better 
	 */
	@Override
	public int getMove(Game g) {
		if(g.getLimitMove() >= g.getPile()){
			return g.getPile();
		}
		else if(g.getPile() == g.getLimitMove() + 1){
			return 2;
		}
		else if(g.getPile() <= 100 && g.getPile()%2==0){
			return 1;
		}
		else return 2;
	}

	/**
	 * Returns the name Jack
	 */
	@Override
	public String getName() {
		return name;
	}
}
