package entities.weapons;

import game.Ressources;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Fireball extends Weapon {

	protected BufferedImage image;
	
	
	
	//TODO: Entity doit afficher lui même ses entitées !!!!!!!!!!!!!!!!!!!!!!!
	//Renomer Entity en Entities !!!!!!!!!!!!!!!!!!
	
	
	
	
	public Fireball(int x, int y){
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
	}
	
	@Override
	public void compute() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
