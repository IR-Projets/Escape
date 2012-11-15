package entities.ships;

import java.awt.image.BufferedImage;

import org.jbox2d.common.Vec2;

import effects.Effects;
import effects.Explosion;
import entities.Bonus;
import entities.CollisionGroup;
import entities.Entities;
import entities.Entity;
import entities.weapons.WeaponItems;
import game.Ressources;
import game.Variables;

public class Player extends Ship {

	private final WeaponItems weaponItems;
	private BufferedImage[] invicibleImages;
	private BufferedImage[] loopingImages;
	private BufferedImage[] touchedImages;

	
	private final int LOOPING_STEP = 40;
	private final int INVICIBLE_STEP = 80;
	private final int INVICIBLE_REPEAT = 1;
	private final int TOUCHED_STEP = 100;
	
	private boolean invicible;
	private boolean touched;
	private boolean looping;
	
	private int step;
	private int repeat;
	private int currentFrame;

	public enum Direction{
		NONE,
		LEFT,
		RIGHT;
	}
	private Direction loopDirection;

	
	public Player(Entities entities){
		super(entities, EntityShape.Polygon, Ressources.getImage("ships/playerShip/Joueur.png"), Variables.SCREEN_WIDTH/2, Variables.SCREEN_HEIGHT/5, Variables.SHIP_LIFE);
		weaponItems = new WeaponItems();
		
		invicible = false;
		touched = false;
		looping = false;
		
		step=0;
		currentFrame=0;
		
		/*
		 * Set images here
		 */
		invicibleImages = new BufferedImage[5];
		loopingImages = new BufferedImage[22];
		touchedImages = new BufferedImage[4];
		
		for(int i=0; i<invicibleImages.length; i++){
			invicibleImages[i] = Ressources.getImage("ships/playerShip/Joueur_invicible"+(i+1)+".png");	
		}
		for(int i=0; i<loopingImages.length; i++){
			loopingImages[i] = Ressources.getImage("ships/playerShip/Joueur_loop"+(i+1)+".png");	
		}
		for(int i=0; i<touchedImages.length; i++){
			touchedImages[i] = Ressources.getImage("ships/playerShip/Joueur_red"+(i+1)+".png");
		}
		
		
		getBody().setFixedRotation(true);
		setCollisionListener(true);
		setCollisionGroup(EntityType.Joueur);
		setDamping(Variables.SHIP_DAMPING);
	}
	
	private void setCollisionListener(boolean isListener){
		setCollisionGroup(EntityType.Joueur);
		if(!isListener){
			getBody().getFixtureList().getFilterData().maskBits = CollisionGroup.BONUS_COLLISION;
		}
	}

	@Override
	public void compute() {
		Vec2 pos = getPositionNormalized();
		Vec2 vel = getVelocity();
		
		
		/*
		 * Player limits
		 */
		if( (vel.x<0 && pos.x<0) ||
				(vel.x>0 && pos.x>Variables.SCREEN_WIDTH - getImage().getWidth()))
			vel.x=0;

		if( (vel.y<0 && pos.y<getImage().getHeight()) || 
				(vel.y>0 && pos.y>Variables.SCREEN_HEIGHT/3 + getImage().getHeight()))
			vel.y=0;

		setVelocity(vel.x, vel.y);
	}


	/*
	 * All renders
	 * @see entities.ships.Ship#getImage()
	 */
	@Override
	public BufferedImage getImage() {		
		step++;
		if(invicible){
			return invincibleRender();
		}		
		else if(touched){
			return touchedRender();
		}
		else if(looping){
			return loopRender();
		}		
		else{
			return super.getImage();
		}
	}
	
	private BufferedImage invincibleRender(){
		BufferedImage image = invicibleImages[currentFrame];
		if(step>INVICIBLE_STEP){
			step=0;		
			currentFrame++;
			if(currentFrame>=invicibleImages.length){
				currentFrame=0;
				repeat++;
			}
			if(repeat>INVICIBLE_REPEAT){
				repeat=0;
				setInvicible(false);
			}
		}
		return image;		
	}
	
	private BufferedImage touchedRender(){
		BufferedImage image = touchedImages[currentFrame];
		if(step>TOUCHED_STEP){
			step=0;			
			currentFrame++;
			if(currentFrame>=touchedImages.length){
				setTouched(false);
				setInvicible(true);
			}
		}
		return image;		
	}
	
	private BufferedImage loopRender(){	
		BufferedImage image = loopingImages[currentFrame];
		if(step>LOOPING_STEP){
			step=0;			
			switch(loopDirection){
			case LEFT:
				currentFrame--;
				break;
			case RIGHT:
				currentFrame++;
				break;
			}
			
			if(currentFrame<0 || currentFrame>=loopingImages.length){
				setLooping(Direction.NONE);
			}
		}
		return image;
	}
	
	
	
	/*
	 * Animation setters
	 */
	public void setLooping(Direction direction){
		step=0;
		invicible=false;
		touched=false;
		loopDirection = direction;
		
		switch(direction){
		case NONE:
			looping=false;
			currentFrame=0;
			setCollisionListener(true);
			break;
		case LEFT:
			looping=true;
			currentFrame = loopingImages.length-1;
			setCollisionListener(false);
			break;
		case RIGHT:
			looping=true;
			currentFrame = 0;
			setCollisionListener(false);
			break;
		} 
	}
	
	private void setInvicible(boolean invicible){
		this.invicible=invicible;
		touched=false;
		looping=false;
		currentFrame=0;
		step=0;
		
		setCollisionListener(!invicible);
	}
	
	private void setTouched(boolean touched){
		this.touched=touched;
		currentFrame=0;
		step=0;
		setCollisionListener(!touched);
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
			setTouched(true);
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

}
