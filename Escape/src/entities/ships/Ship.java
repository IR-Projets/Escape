package entities.ships;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entities.Entity;
import game.Variables;
import hud.LifeListener;



public abstract class Ship extends Entity{
	private List<LifeListener> lifeListener = new ArrayList<LifeListener>();
	private int life;
	
	public Ship(){
		this(Variables.MAX_LIFE);
	}
	
	public Ship(int life){
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
