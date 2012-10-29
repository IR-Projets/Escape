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

	/*
	 * Sauvegarde une association entre body et entity
	 * Un seul World et une seule liste d'entity
	 */
	protected static Hashtable<Body, Entity> entities;
	protected static World world;
	
	
	protected Body body;
	protected CollisionListener collisionListener;
	
	
	public Entity() {
		entities = new Hashtable<>();
	}
	
	
	/**
	 * Init the dynamic render of an Entity
	 * @param world The world where the entity will be added
	 * @param x position x on the screen
	 * @param y position y on the screen
	 */
	public void init(World world, float x, float y){
		Entity.world = world;
		
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
	
	public void setCollisionListener(CollisionListener listener){
		this.collisionListener = listener;
	}
	
	public void render(Graphics2D graphics){

		BufferedImage image = getImage();
		
		AffineTransform tx = new AffineTransform();
		tx.rotate(getRotate(), image.getWidth()/2, image.getHeight()/2);
		
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BICUBIC);

		graphics.drawImage(op.filter(image, null), toScreenSize(body.getPosition().x) - image.getWidth()/2, Variables.SCREEN_HEIGHT - toScreenSize(body.getPosition().y) - image.getHeight()/2, image.getWidth(), image.getHeight(), null );
	}
	

	public float getRotate(){
		return body.getAngle();
	}	
	
	public Vec2 getScreenPostion(){
		return new Vec2(toScreenSize(body.getPosition().x), toScreenSize(body.getPosition().y));
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
	
	protected void contact(Body contact){
		if(collisionListener!=null)
			collisionListener.collide(entities.get(contact));	//On le convertit en Entity (pour la classe Environnement)
	}
}
