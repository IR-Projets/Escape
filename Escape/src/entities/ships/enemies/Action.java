package entities.ships.enemies;


public class Action {
	public enum ActionType{
		Fire,
		Move
	};

	public int beg;
	public int end;
	public int velocity;
	public double angle;
	public ActionType type;
	public String name;


	public Action(){
		beg=-1;
		end=-1;
		velocity=Integer.MAX_VALUE;
		angle=Integer.MAX_VALUE;
		type=null;
		name=null;
	}
}