package worlds;

import game.Variables;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

public abstract class Entity {

	protected World world;
	protected Body body;
	protected BufferedImage image;
	
	public Entity() {
	}
	
	
	/**
	 * Init the dynamic render of an Entity
	 * @param world The world where the entity will be added
	 * @param x position x on the screen
	 * @param y position y on the screen
	 */
	public void init(World world, float x, float y){
		this.world = world;
		
		image = getImage();
		
		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(toWorldSize(image.getWidth()/2), toWorldSize(image.getHeight()/2));

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.position.set(x / Variables.WORLD_SCALE, y / Variables.WORLD_SCALE);
		bodyDef.allowSleep = false;
		bodyDef.fixedRotation = true;
		body = world.createBody(bodyDef);
		body.createFixture(polygonShape, 1.0f);
	}
	
	
	public void move(int x, int y){
		body.setLinearVelocity(new Vec2(toWorldSize(x), toWorldSize(-y)));
	}	
	
	public void render(Graphics2D graphics){

		AffineTransform tx = new AffineTransform();
		tx.rotate(getRotate(), image.getWidth()/2, image.getHeight()/2);
		
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BICUBIC);

		graphics.drawImage(op.filter(image, null), toScreenSize(body.getPosition().x) - image.getWidth()/2, Variables.SCREEN_HEIGHT - toScreenSize(body.getPosition().y) - image.getHeight()/2, image.getWidth(), image.getHeight(), null );
	}
	
	
	
	
	/*
	 * Methodes protected
	 */
	protected int toScreenSize(float val){
		return (int) (val * Variables.WORLD_SCALE);
	}
	
	protected float toWorldSize(int val){
		return val / Variables.WORLD_SCALE;
	}
	
	protected float getRotate(){
		return body.getAngle();
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
