package listeners;

public interface EnvironnementListener {
	public enum GameState{
		Paused,
		Loose,
		Win
	}
	public void stateChanged(GameState state);
}
