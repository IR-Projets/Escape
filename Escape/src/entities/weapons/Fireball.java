package entities.weapons;

import java.awt.image.BufferedImage;

import org.jbox2d.dynamics.World;

import entities.Entities;
import game.Ressources;

public class Fireball extends Weapon {

	protected BufferedImage image;
	
	
	
	//TODO: Entity doit afficher lui même ses entitées !!!!!!!!!!!!!!!!!!!!!!!
	//Renomer Entity en Entities !!!!!!!!!!!!!!!!!!
	
	
	public Fireball(Entities entities, int x, int y) {
		super(entities, Ressources.getImage("images/weapons/fire.png"),x,y);
	}
	
	/*public Fireball(int x, int y){
		image = Ressources.getImage("images/weapons/fire.png");
		init(x,y);
	}

	@Override
	public BufferedImage getImage() {
		return image;
	}

	@Override
	public BufferedImage getIcon() {
		return image;
	}*/
	
	@Override
	public void compute() {
		// TODO Auto-generated method stub
		
	}

	//la il faudra ajouter l'effet d'augmentation de la boule
	/*
	@Override
	public BufferedImage getImageRender() {
		
		return null;
	}*/
	
	
	
}
