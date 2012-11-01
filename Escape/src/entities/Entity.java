package entities;

import game.Variables;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Hashtable;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

public abstract class Entity {

	protected Body body;
	
	/*
	 * Sauvegarde une association entre body et entity
	 * Un seul World et une seule liste d'entity
	 */
	protected static Hashtable<Body, Entity> entities = new Hashtable<>();
	protected static World world;
	public static void setWorld(World newWorld) {
		world = newWorld;	
	}
	public static void step() {
		world.step(Variables.WORLD_TIME_STEP, Variables.WORLD_VELOCITY_ITERATION, Variables.WORLD_POSITION_ITERATION);			
	}
	
	
	/**
	 * Init the dynamic render of an Entity
	 * @param world The world where the entity will be added
	 * @param x position x on the screen
	 * @param y position y on the screen
	 */
	public void init(float x, float y){
		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(toWorldSize(getImage().getWidth()/2), toWorldSize(getImage().getHeight()/2));

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.position.set(x / Variables.WORLD_SCALE, y / Variables.WORLD_SCALE);
		bodyDef.allowSleep = false;
		body = world.createBody(bodyDef);
		body.createFixture(polygonShape, 1.0f);
		entities.put(body, this);
	}
	
	public void render(Graphics2D graphics){

		BufferedImage image = getImage();
		
		AffineTransform tx = new AffineTransform();
		tx.rotate(getRotate(), image.getWidth()/2, image.getHeight()/2);
		
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BICUBIC);

		Vec2 pos = getScreenPostion();
		
		
		graphics.drawImage(op.filter(image, null),
								(int)pos.x,
								(int)pos.y,
								image.getWidth(), 
								image.getHeight(),
								null );
	}
	

	public float getRotate(){
		return body.getAngle();
	}	
	
	public Vec2 getScreenPostion(){
		BufferedImage image = getImage();
		return new Vec2(toScreenSize(body.getPosition().x) - image.getWidth()/2, 
						Variables.SCREEN_HEIGHT - (toScreenSize(body.getPosition().y) + image.getHeight()/2));
	}
	
	public Vec2 getPositionNormalized(){
		BufferedImage image = getImage();
		return new Vec2(toScreenSize(body.getPosition().x) - image.getWidth()/2, 
						toScreenSize(body.getPosition().y) + image.getHeight()/2);
	}

	public boolean isOnSprite(Vec2 point){
		Vec2 position = getScreenPostion();
		BufferedImage image = getImage();
		
		return point.x>position.x && point.x<position.x+image.getWidth() &&
				point.y>position.y && point.y<position.y+image.getHeight();
	}
	
	public void setVelocity(float speedX, float speedY){
		body.setLinearVelocity(new Vec2(toWorldSize(speedX), toWorldSize(speedY)));
	}	
	
	public Vec2 getVelocity(){
		return body.getLinearVelocity();
	}
	
	public void stop(){
		setVelocity(0,0);
	}
	
	public Body getBody(){
		return body;
	}
	
	public static Entity getEntity(Body body){
		return entities.get(body);
	}
	
	public static void removeEntity(Entity entity){
		world.destroyBody(entity.getBody());
		entities.remove(entity.getBody());		
	}
	
	
	
	
	/*
	 * Methodes protected
	 */
	protected int toScreenSize(float val){
		return (int) (val * Variables.WORLD_SCALE);
	}
	
	protected float toWorldSize(float speedX){
		return speedX / Variables.WORLD_SCALE;
	}
	
	


	
	/*
	 * Methodes abstraites
	 */
	public abstract void compute();
	public abstract BufferedImage getImage();
	
	
	
	/*
	 * Private
	 */
	private void debug(){
	     //world.step(TIME_STEP, VELOCITY_ITERATION, POSITION_ITERATION);
	     Vec2 position = body.getPosition();
	     float angle = body.getAngle();
	     System.out.printf("X:%4.2f, Y:%4.2f, Angle:%4.2f\n", position.x, position.y, angle);
	     //System.out.printf("X:%4.2f, Y:%4.2f, Angle:%4.2f\n", getX(), getY(), angle);
	}
}
