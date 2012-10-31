package entities.ships;

import java.io.IOException;
import java.util.List;

import entities.Entity;
import game.Variables;



public abstract class Ship extends Entity{
	private List<LifeListener> lifeListener;

	private int posX;
	private int posY;
	private int life;
	
	public Ship() throws IOException{
		this(Variables.MAX_LIFE);
	}
	
	public Ship(int life) throws IOException{
		this.life=life;
	}
	
	public void addListener(LifeListener listen){
		lifeListener.add(listen);
	}
	
	public List<LifeListener> getLifeListener() {
		return lifeListener;
	}
	
	public void setLife(int life){
		for(LifeListener listen : lifeListener)
			listen.lifeChanged(this.life, life);
	}
	
	public int getLife(){
		return life;
	}
}
