package entities.weapons;

import game.Ressources;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Fireball extends Weapon {

	public Fireball(){
		currentImage = Ressources.getImage("images/weapons/fire.png");		
	}
}
