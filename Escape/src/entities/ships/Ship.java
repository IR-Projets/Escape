package entities.ships;

import hud.LifeListener;

import java.util.ArrayList;
import java.util.List;
import org.jbox2d.dynamics.World;
import entities.Entitie;



public abstract class Ship extends Entitie{
	private List<LifeListener> lifeListener = new ArrayList<LifeListener>();
	private int life;
	
	/*public Ship(){
		this(Variables.MAX_LIFE);
	}*/
	
	public Ship(World world, String nameImage, float x, float y, int life){
		super(world,nameImage,x,y);
		this.life=life;
	}
	
	public void addListener(LifeListener listen){
		lifeListener.add(listen);
	}
	
	public void setLife(int life){
		for(LifeListener listen : lifeListener)
			listen.lifeChanged(this.life, life);
		this.life = life;
	}
	
	public int getLife(){
		return life;
	}
}
