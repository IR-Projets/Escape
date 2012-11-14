package listeners;

public interface EnvironnementListener {
	public enum GameState{
		Run,
		Paused,
		Loose,
		Win
	}
	public void stateChanged(GameState state);
}
