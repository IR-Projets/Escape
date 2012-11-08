package entities.ships;

import effects.Effects;
import effects.Explosion;
import entities.Entities;
import entities.Entity;
import game.Ressources;
import game.Variables;

import java.awt.image.BufferedImage;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.DistanceJointDef;
import org.jbox2d.dynamics.joints.Joint;

public class Player extends Ship {

	private final static int SLOW = 45;

	private BufferedImage[] loopingImage;

	//Utilise sa pour charger les images !!!!!!!!!!!!!!!!!!!!!!!!!
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//Class loader
	//getRessourceAsStream(String name)
	//FAIRE NOTRE CLASS LOADER


	public enum Looping{
		NONE,
		LEFT,
		RIGHT;

		//BufferedImage currentImage;
		int frame = 0;
		int count = 0;
	}
	private Looping looping;



	public Player(Entities entities){
		super(entities, Ressources.getImage("images/ships/player/Joueur.png"), Variables.SCREEN_WIDTH/2, Variables.SCREEN_HEIGHT/5, Variables.MAX_LIFE);
		
		getBody().setFixedRotation(true);
		looping = Looping.NONE;


		loopingImage = new BufferedImage[12];
		for(int i=0; i<loopingImage.length; i++){
			loopingImage[i] = Ressources.getImage("images/ships/player/Joueur"+(i+1)+".png");	
		}
	}

	@Override
	public void compute() {
		Vec2 position = getPositionNormalized();
		Vec2 velocity = getVelocity();

		if(position.y > getImage().getHeight() + Variables.SCREEN_HEIGHT/3 ){
			setVelocity(velocity.x*20, -10);
		}		
	}

	@Override
	public BufferedImage getImage() {
		switch(looping){
		case NONE:		
			return super.getImage();

		case LEFT:
			looping.count = ++looping.count % SLOW;
			if(looping.count==0)
				looping.frame--;
			System.out.println("looping frame"+looping.frame);
			return loopRender();

		case RIGHT:
			looping.count = ++looping.count % SLOW;
			if(looping.count==0)
				looping.frame++;
			return loopRender();
		}
		return null;
	}

	
	private BufferedImage loopRender(){				
		if(looping.frame<0 || looping.frame>=loopingImage.length){
			looping.frame = 0;
			looping = Looping.NONE;
			return getImage();
		}

		switch(looping){
		case LEFT:
			return loopingImage[looping.frame];
		case RIGHT:
			return loopingImage[looping.frame];
		case NONE:
		default:
			break;
		}
		return getImage();
	}

	/*
	 * Actions
	 */
	public void looping(Looping loop){
		looping = loop;
		switch(looping){
		case NONE:
			looping.frame=0;
			break;
		case LEFT:
			looping.frame= loopingImage.length-1;
			break;
		case RIGHT:
			looping.frame = 0;
			break;
		} 
	}

	@Override
	public EntityType getType() {
		return EntityType.Joueur;
	}

	@Override
	public void collision(Entity entity, EntityType type) {
		System.out.println(type);
		switch(type){
		case Boss:
		case Enemy:
		case WeaponEnnemy:
			if(getLife()>10)
				setLife(getLife()-10);
			else{
				Vec2 pos = getScreenPostion();
				Effects.addEffect(new Explosion((int)pos.x, (int)pos.y));
				getEntities().removeEntitie(this);
			}
			break;
		case WeaponAllied :
			break;
		}
		
	}


	/**
	 * Used to join the Ship to the origin point
	 * Non used
	 *
	private void initJoin(World world){
		Joint joint;
		Body ground = world.createBody(new BodyDef());

		DistanceJointDef jd = new DistanceJointDef();
		Vec2 p1 = new Vec2();
		Vec2 p2 = new Vec2();
		Vec2 d = new Vec2();

		jd.frequencyHz = Variables.LINK_FREQUENCY;
		jd.dampingRatio = Variables.LINK_DAMPING;

		jd.bodyA = ground;
		jd.bodyB = getBody();
		jd.localAnchorA.set(toWorldSize(Variables.SCREEN_WIDTH/2), toWorldSize(Variables.SCREEN_HEIGHT/5));
		jd.localAnchorB.set(0,0);
		p1 = jd.bodyA.getWorldPoint(jd.localAnchorA);
		p2 = jd.bodyB.getWorldPoint(jd.localAnchorB);
		d = p2.sub(p1);
		jd.length = 0;//d.length();
		joint = world.createJoint(jd);
	}
	*/

}
