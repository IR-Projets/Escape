package listeners;

public interface GameStateListener {
	public enum GameState{
		Paused,
		Loose,
		Win
	}
	public void stateChanged(GameState state);
}
