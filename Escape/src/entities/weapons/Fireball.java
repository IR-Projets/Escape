package entities.weapons;

import java.awt.image.BufferedImage;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import entities.Entities;
import game.Ressources;

public class Fireball extends Weapon {

	protected BufferedImage image;
	
	
	
	//TODO: Entity doit afficher lui m�me ses entit�es !!!!!!!!!!!!!!!!!!!!!!!
	//Renomer Entity en Entities !!!!!!!!!!!!!!!!!!
	
	
	public Fireball(Entities entities, int x, int y, boolean firedByPlayer) {
		super(entities, EntityShape.Circle, Ressources.getImage("images/weapons/fire.png"),x,y, firedByPlayer);
	}

	
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
