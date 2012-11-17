package listeners;

public interface EnvironnementListener {
	public enum GameState{
		Loose,
		Win
	}
	public void stateChanged(GameState state);
}
