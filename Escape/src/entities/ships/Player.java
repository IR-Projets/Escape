package entities.ships;

import effects.Effects;
import effects.Explosion;
import entities.Bonus;
import entities.Entities;
import entities.Entity;
import entities.weapons.WeaponItems;
import factories.WeaponFactory.WeaponType;
import game.Ressources;
import game.Variables;
import gestures.TraceStack;

import java.awt.image.BufferedImage;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.Filter;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.DistanceJointDef;
import org.jbox2d.dynamics.joints.Joint;

public class Player extends Ship {

	private final static int TICK_SKIP = 60;

	private BufferedImage[] loopingImages;
	private BufferedImage[] touchedImages;
	private boolean touched;
	
	private final WeaponItems weaponItems;

	public enum Looping{
		NONE,
		LEFT,
		RIGHT;

		//BufferedImage currentImage;
		int frame = 0;
		int count = 0;
	}
	private Looping looping;

//if ((catA & maskB) != 0 && (catB & maskA) != 0) //collide

	public Player(Entities entities){
		super(entities, EntityShape.Polygon, Ressources.getImage("ships/player/Joueur.png"), Variables.SCREEN_WIDTH/2, Variables.SCREEN_HEIGHT/5, Variables.MAX_LIFE);

		getBody().setFixedRotation(true);
		getBody().getFixtureList().getFilterData().categoryBits = 0x04;
		getBody().getFixtureList().getFilterData().maskBits = 0x06;
		
		//getBody().getFixtureList().getFilterData().groupIndex = -2;

		weaponItems = new WeaponItems();
		
		looping = Looping.NONE;
		

		loopingImages = new BufferedImage[12];
		touchedImages = new BufferedImage[2];
		touched=false;
		
		for(int i=0; i<loopingImages.length; i++){
			loopingImages[i] = Ressources.getImage("ships/player/Joueur"+(i+1)+".png");	
		}
		for(int i=0; i<touchedImages.length; i++){
			touchedImages[i] = Ressources.getImage("ships/player/Joueur_red"+(i+1)+".png");
		}
	}

	@Override
	public void compute() {
		Vec2 pos = getPositionNormalized();
		Vec2 vel = getVelocity();
		
		//limites du monde pour empecher le joueur de sortir

		if( (vel.x<0 && pos.x<0) ||
				(vel.x>0 && pos.x>Variables.SCREEN_WIDTH - getImage().getWidth()))
			vel.x=0;

		if( (vel.y<0 && pos.y<getImage().getHeight()) || 
				(vel.y>0 && pos.y>Variables.SCREEN_HEIGHT/3 + getImage().getHeight()))
			vel.y=0;

		setVelocity(vel.x, vel.y);
	}

	private int touchedImage=0;
	private int count=0;
	@Override
	public BufferedImage getImage() {
		
		switch(looping){
		case NONE:		
			if(touched){
				count++;
				BufferedImage image = touchedImages[touchedImage];
				if(count>TICK_SKIP){
					count=0;
					touchedImage++;
					if(touchedImage>=2){
						touchedImage=0;
						touched=false;
					}
				}
				return image;
			}
			else
				return super.getImage();

		case LEFT:
			looping.count = ++looping.count % TICK_SKIP;
			if(looping.count==0)
				looping.frame--;
			return loopRender();

		case RIGHT:
			looping.count = ++looping.count % TICK_SKIP;
			if(looping.count==0)
				looping.frame++;
			return loopRender();
		}
		return null;
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
			looping.frame= loopingImages.length-1;
			break;
		case RIGHT:
			looping.frame = 0;
			break;
		} 
	}

	private BufferedImage loopRender(){				
		if(looping.frame<0 || looping.frame>=loopingImages.length){
			looping.frame = 0;
			looping = Looping.NONE;
			return getImage();
		}

		switch(looping){
		case LEFT:
			return loopingImages[looping.frame];
		case RIGHT:
			return loopingImages[looping.frame];
		case NONE:
		default:
			break;
		}
		return getImage();
	}
	
	@Override
	public EntityType getEntityType() {
		return EntityType.Joueur;
	}

	@Override
	public int getDamage() {
		return 10;
	}
	
	@Override
	public void collision(Entity entity, EntityType type) {
		switch(type){
		case Boss:
		case Enemy:
		case WeaponEnnemy:
			touched=true;
			setLife(getLife()-entity.getDamage());
			if(getLife()<10){
				Vec2 pos = getScreenPostion();
				Effects.addEffect(new Explosion((int)pos.x, (int)pos.y));
				getEntities().removeEntitie(this);
			}
		break;
	case Bonus :
		Bonus bonus = (Bonus) (entity);
		weaponItems.addWeaponItem(bonus.getWeaponType(), bonus.getQuantity());
		getEntities().removeEntitie(entity);
		break;
	}

}
	
	
	public WeaponItems getWeapons(){
		return weaponItems;
	}

	public void loadWeapon() {
		if(!weaponItems.isEmpty())
			loadWeapon(weaponItems.getCurrentWeaponItem().getWeaponType(), true);
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
